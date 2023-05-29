package com.example.helgather.src.Main.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileSbdBinding
import com.example.helgather.databinding.ProfileSbdListBinding
import com.example.helgather.src.Main.profile.ProfileFragmentInterface
import com.example.helgather.src.Main.profile.ProfileService
import com.example.helgather.src.Main.profile.list.ProfileSBDAdapter
import com.example.helgather.src.Main.profile.model.GetProfileResponse
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetSBDResult
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResponse
import com.example.helgather.src.Main.profile.model.PostSBDResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import com.example.helgather.util.LoadingDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class ProfileSBDFragment : BaseFragment<FragmentProfileSbdBinding> (FragmentProfileSbdBinding::bind , R.layout.fragment_profile_sbd),
    ProfileFragmentInterface {

    private lateinit var videoURI: Uri
    private var sbd : String = ""
    private var sbdUrl : String = ""
    private var memberId : Int = 0
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
//    private lateinit var takeVideoLauncher: ActivityResultLauncher<Uri>
    private lateinit var chooseVideoLauncher: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[android.Manifest.permission.CAMERA] == true) {
                showVideoDialog()
            } else {
                showToastMessage("앱에서 권한을 요청하는데 문제가 발생했습니다.")
            }
        }
//        takeVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val videoUri = result.data?.data
//                if (videoUri != null) {
//                    uploadVideo(videoUri)
//                } else {
//                    showToastMessage("영상을 찍는 과정에서 오류가 발생하였습니다.")
//                }
//            } else {
//                showToastMessage("영상을 찍는 과정에서 오류가 발생하였습니다.")
//            }
//        }

        chooseVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                videoURI = uri

                val videoRequestBody = createRequestBodyFromUri(uri)
                val videoBody = MultipartBody.Part.createFormData("file", "video.mp4", videoRequestBody)

                // Call your service here to upload the video.
                ProfileService(this@ProfileSBDFragment).tryPostSBD(memberId,sbd,videoBody)
            } else {
                showToastMessage("영상을 선택하는 과정에서 오류가 발생하였습니다.")
            }
        }


        ProfileService(this@ProfileSBDFragment).tryGetSBD(member = memberId)

    }

    private fun uploadVideo(videoUri: Uri) {
        val videoRequestBody = createRequestBodyFromUri(videoUri)
        val videoBody = MultipartBody.Part.createFormData("file", "video.mp4", videoRequestBody)

        // Call your service here to upload the video.
        ProfileService(this@ProfileSBDFragment).tryPostExercise(memberId, videoBody)
    }

    private fun createRequestBodyFromUri(uri: Uri): RequestBody {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val byteArray = inputStream?.readBytes()
        return byteArray!!.toRequestBody("video/mp4".toMediaTypeOrNull())
    }

    private fun showVideoDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("비디오 선택")
        val pictureDialogItems = arrayOf("카메라에서 비디오 촬영", "갤러리에서 비디오 선택")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
//                0 -> takeVideoLauncher.launch(null)
                1 -> chooseVideoLauncher.launch("video/*")
            }
        }
        pictureDialog.show()
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        var filePath = ""
        requireContext().contentResolver.query(uri, null, null, null, null)?.apply {
            val columnIndex = getColumnIndex(MediaStore.Video.Media.DATA)
            moveToFirst()
            filePath = getString(columnIndex)
            close()
        }
        return filePath
    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }





    override fun onGetSBDSuccess(response: GetSBDResponse) {
        binding.rvProfileSbd.apply {
            adapter = ProfileSBDAdapter(response.getSBDResult,object : ProfileSBDAdapter.ProfileSBDClickListener<GetSBDResult>{
                override fun onUploadClick(view: ProfileSbdListBinding, pos: Int) {
                    sbd = response.getSBDResult[pos].category.lowercase()
                    //여기에 이제 통신을 해야함
                    showVideoDialog()

                }

                override fun onVideoClick(view: ProfileSbdListBinding, pos: Int) {
                    sbdUrl = response.getSBDResult[pos].videoUrl
                    val bundle = Bundle()
                    bundle.putString("sbdUrl", sbdUrl)

                    val fragment = ProfileSBDVideoFragment()
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frm_main,fragment).addToBackStack("video").commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetSBDFailure(message: String) {
        showToastMessage("오류 : $message")
    }

    override fun onPostSBDSuccess(response: PostSBDResponse) {
        Log.d("postSBD",response.message)
        Log.d("postSBD",response.isSuccess.toString())
        Log.d("postSBD",response.code.toString())
        Log.d("postSBD",response.postSBDResult.toString())

    }

    override fun onPostSBDFailure(message: String) {
        showToastMessage("오류 : $message")
    }

    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {}
    override fun onGetTodayExerciseFailure(message: String) {}
    override fun onPostTodayExerciseSuccess(response: PostTodayExerciseResponse) {}
    override fun onPostTodayExerciseFailure(message: String) {}
    override fun onPatchProfileImageSuccess(response: PatchProfileImageResponse) {}
    override fun onPatchProfileImageFailure(message: String) {}
    override fun onPatchProfileIntroductionSuccess(response: PatchProfileIntroductionResponse) {}
    override fun onPatchProfileIntroductionFailure(message: String) {}

    override fun onGetProfileSuccess(response: GetProfileResponse) {}

    override fun onGetProfileFailure(message: String) {}
}