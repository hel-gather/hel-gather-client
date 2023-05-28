package com.example.helgather.src.Main.profile.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileBinding
import com.example.helgather.src.Main.profile.ProfileFragmentInterface
import com.example.helgather.src.Main.profile.ProfileService
import com.example.helgather.src.Main.profile.list.ProfileTabAdapter
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import com.example.helgather.util.ImageUtil
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile),
    ProfileFragmentInterface {

    private var memberId  = ApplicationClass.sSharedPreferences.getInt("memberId",0)
    private lateinit var photoURI: Uri
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Uri>
    private lateinit var choosePhotoLauncher: ActivityResultLauncher<String>
    private var uploadType = 0 // 1 -> 프로필 사진 편집, 2 오늘의 운동 인증

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("profiletest",memberId.toString())

        memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)

        tabLayoutControl()

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true) {
                showPictureDialog()
            } else {
                showToastMessage("엡애서 권한을 요청하는데 문제가 발생했습니다.")
            }
        }
        takePhotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                val file = File(getRealPathFromURI(photoURI)!!)

                val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", "image.png", requestBody)
                if(uploadType == 1){
                    Glide.with(this).load(photoURI).into(binding.civProfileImage)
                    ProfileService(this@ProfileFragment).tryPatchProfile(memberId,body)
                }else if(uploadType == 2){
                    ProfileService(this@ProfileFragment).tryPostExercise(memberId,body)
                }
            } else {
                showToastMessage("사진을 찍는 과정에서 오류가 발생하였습니다.")
            }
        }

        choosePhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                photoURI = uri
                val file = File(getRealPathFromURI(photoURI)!!)

                val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", "image.png", requestBody)
                if(uploadType == 1){
                    Glide.with(this).load(uri).into(binding.civProfileImage)
                    ProfileService(this@ProfileFragment).tryPatchProfile(memberId,body)
                }else if(uploadType == 2){
                    ProfileService(this@ProfileFragment).tryPostExercise(memberId,body)
                }
            } else {
                showToastMessage("사진을 선택하는 과정에서 오류가 발생하였습니다.")
            }
        }


        binding.civProfileImage.setOnClickListener {
            uploadType = 1
            if (hasPermissions()) {
                showPictureDialog()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                } else {
                    showToastMessage("앱에서 권한을 요청하는데 문제가 발생하였습니다.")
                }
            }
        }

        binding.btnProfileExercise.setOnClickListener {
            uploadType = 2
            if (hasPermissions()) {
                showPictureDialog()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                } else {
                    showToastMessage("앱에서 권한을 요청하는데 문제가 발생하였습니다.")
                }
            }
        }

    }
    private fun tabLayoutControl(){
        binding.vpProfile.apply {
            adapter = ProfileTabAdapter(this@ProfileFragment)
        }
        TabLayoutMediator(binding.tabLayout,binding.vpProfile){ tab, position ->
            when(position){
                0-> tab.text = "SBD 운동 인증"
                1-> tab.text = "오늘의 운동 인증"
            }
        }.attach()
    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("프로필 사진 변경")
        val pictureDialogItems = arrayOf("카메라에서 사진 찍기", "갤러리에서 사진 선택")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> {
                    try {
                        val file = createImageFile()
                        photoURI = FileProvider.getUriForFile(requireContext(), "com.example.helgather.fileprovider", file)
                        takePhotoLauncher.launch(photoURI)
                    } catch (e: IOException) {
                        // 에러 처리
                    }
                }
                1 -> choosePhotoLauncher.launch("image/*")
            }
        }
        pictureDialog.show()
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        var sourceBitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(uri))

        sourceBitmap = ImageUtil.rotateImageIfRequired(requireContext(), sourceBitmap, uri)

        // Resize the bitmap.
        val width = (sourceBitmap.width * 0.15).toInt()
        val height = (sourceBitmap.height * 0.15).toInt()
        val resizedBitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, true)

        // Save the resized bitmap into a temporary file.
        val tempFile = File.createTempFile("temp", ".png", requireContext().cacheDir)
        FileOutputStream(tempFile).use {
            // Compress the bitmap as a PNG file.
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        // Clean up the source bitmap.
        sourceBitmap.recycle()

        // Return the path of the temporary file.
        return tempFile.absolutePath
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            photoURI = FileProvider.getUriForFile(requireContext(), "com.example.helgather.fileprovider", this)
        }
    }


    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {

    }

    override fun onGetTodayExerciseFailure(message: String) {
    }

    override fun onPostTodayExerciseSuccess(response: PostTodayExerciseResponse) {
    }

    override fun onPostTodayExerciseFailure(message: String) {
    }

    override fun onGetSBDSuccess(response: GetSBDResponse) {
    }

    override fun onGetSBDFailure(message: String) {
    }

    override fun onPatchProfileImageSuccess(response: PatchProfileImageResponse) {
    }

    override fun onPatchProfileImageFailure(message: String) {
    }
}