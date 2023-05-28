package com.example.helgather.src.Login

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivitySignupImageBinding
import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostSignUpImageResponse
import com.example.helgather.src.Login.model.PostSignUpProfileResponse
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.example.helgather.src.Main.MainActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpImageActivity : BaseActivity<ActivitySignupImageBinding>(ActivitySignupImageBinding::inflate), LoginInterface {

    private lateinit var photoURI: Uri
    private var memberId : Int = 0
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Uri>
    private lateinit var choosePhotoLauncher: ActivityResultLauncher<String>


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        memberId = intent.getIntExtra("memberId",0)
        memberId = 46


        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true) {
                showPictureDialog()
            } else {
                showToastMessage("엡애서 권한을 요청하는데 문제가 발생했습니다.")
            }
        }
        takePhotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                // 이미지 로딩
                Glide.with(this).load(photoURI).into(binding.civSignUpImage)
            } else {
                // 사진 캡처가 실패한 경우 처리
            }
        }

        choosePhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                photoURI = uri
                //이미지 로딩
                Glide.with(this).load(uri).into(binding.civSignUpImage)
            } else {
                // 이미지 선택이 실패한 경우 처리
            }
        }

        binding.btnSignUpImageSelect.setOnClickListener {
            if (hasPermissions()) {
                showPictureDialog()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                } else {
                    showToastMessage("엡애서 권한을 요청하는데 문제가 발생했습니다.")
                }
            }
        }

        binding.btnSignUpImageComplete.setOnClickListener {
            val file = File(getRealPathFromURI(photoURI)!!)

            val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", "image.png", requestBody)

            LoginService(this@SignUpImageActivity).tryPostSignUpImage(memberId = memberId ,body)
        }

    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("사진 선택")
        val pictureDialogItems = arrayOf("카메라에서 사진 찍기", "갤러리에서 사진 선택")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> {
                    try {
                        val file = createImageFile()
                        photoURI = FileProvider.getUriForFile(this, "com.example.helgather.fileprovider", file)
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
        // Load the image into a Bitmap.
        val sourceBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))

        // Resize the bitmap.
        val width = (sourceBitmap.width * 0.1).toInt()
        val height = (sourceBitmap.height * 0.1).toInt()
        val resizedBitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, true)

        // Save the resized bitmap into a temporary file.
        val tempFile = File.createTempFile("temp", ".png", cacheDir)
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
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            photoURI = FileProvider.getUriForFile(this@SignUpImageActivity, "com.example.helgather.fileprovider", this)
        }
    }

    override fun onPostSignUpImageSuccess(response: PostSignUpImageResponse) {
        if (response.code == 200) {
            val intent = Intent(this@SignUpImageActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        Log.d("----", "${response.postSignUpImageResult}")
    }


    override fun onPostSignUpImageFailure(message: String) {
    }

    override fun onPostLoginSuccess(response: PostLoginResponse) {}
    override fun onPostLoginFailure(message: String) {}
    override fun onPostSignUpSuccess(response: PostSignUpResponse) {}
    override fun onPostSignUpFailure(message: String) {}
    override fun onPostSignuUpProfileSuccess(response: PostSignUpProfileResponse) {}
    override fun onPostSignUpProfileFailure(message: String) {}
}


//    @RequiresApi(Build.VERSION_CODES.P)
//    private fun requestPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
//        }
//    }