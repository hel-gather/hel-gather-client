package com.example.helgather.src.Main.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentHomeBinding
import com.example.helgather.src.Main.home.mainDB.AppDatabase
import com.example.helgather.src.Main.home.mainDB.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind,R.layout.fragment_home) {

    private lateinit var currentPhotoPath: String


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnHomeAuth.setImageResource(R.drawable.ic_auth_done)



        binding.ivHomeAuthHealth.setOnClickListener {
            if (hasPermissions()) {
                showPictureDialog()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions()
                } else {
                    Toast.makeText(requireContext(), "앱에서 권한을 요청하는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        getLastImage()


    }

    override fun onResume() {
        super.onResume()
//        getLastImage()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun requestPermissions() {
        val permissions = mutableListOf(Manifest.permission.CAMERA)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        requestPermissionLauncher.launch(permissions.toTypedArray())
    }


    private fun hasPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.CAMERA] == true
        ) {
            // 모든 권한이 부여되었습니다.
            showPictureDialog()
        } else {
            // 일부 권한이 거부되었습니다.
            Toast.makeText(requireContext(), "권한이 거부되었습니다. 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    //다이얼로그를 통해 사용자 확인
    @RequiresApi(Build.VERSION_CODES.P)
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("사진 선택")
        val pictureDialogItems = arrayOf("카메라에서 사진 찍기", "갤러리에서 사진 선택")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> takePhotoFromCamera()
                1 -> choosePhotoFromGallery()
            }
        }
        pictureDialog.show()
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            binding.ivHomeAuthHealth.setImageBitmap(bitmap)
            saveImageToDB(bitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val bitmap = uriToBitmap(uri)
            binding.ivHomeAuthHealth.setImageBitmap(bitmap)
            saveImageToDB(bitmap)
        }
    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun uriToBitmap(uri: Uri): Bitmap {
        val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
        return ImageDecoder.decodeBitmap(source)
    }


    private fun takePhotoFromCamera() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        photoFile?.let {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.helgather.fileprovider",
                it
            )
            cameraLauncher.launch(photoURI)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun choosePhotoFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun saveImageToDB(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        if (imageData != null) { // imageData가 null이 아닐 때만 ImageEntity를 생성합니다.
            val imageEntity = ImageEntity(0, imageData)

            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(requireContext()).imageDao().insert(imageEntity)
            }
        }
    }


    private fun getLastImage() {
        lifecycleScope.launch(Dispatchers.IO) {
            val image = AppDatabase.getInstance(requireContext()).imageDao().getLastImage()
            image?.let {
                val bitmap = BitmapFactory.decodeByteArray(it.imageData, 0, it.imageData.size)
                withContext(Dispatchers.Main) {
                    binding.ivHomeAuthHealth.setImageBitmap(bitmap)
                }
            } ?: run {
                // 기본 이미지 보여주기
                withContext(Dispatchers.Main) {
                    binding.ivHomeAuthHealth.setImageResource(R.drawable.ic_home_auth_health)
                }
            }
        }
    }

}