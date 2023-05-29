package com.example.helgather.src.Main.recruit.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentRecruitWriteBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.recruit.RecruitFragmentInterface
import com.example.helgather.src.Main.recruit.RecruitService
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailRequest
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse
import com.google.android.exoplayer2.util.Log

class RecruitWriteFragment : BaseFragment<FragmentRecruitWriteBinding>(FragmentRecruitWriteBinding::bind, R.layout.fragment_recruit_write),
    RecruitFragmentInterface {

    private var title : String ?= null
    private var detail : String ?= null
    val region = listOf("강원", "인천", "서울", "경기")
    val regionIndexes = mapOf("강원" to 33, "인천" to 32, "서울" to 2, "경기" to 31)
    private var location = 0
    private var subLocation = 0
    var selectedRegion: String? = null
    private var selectedSubRegion: Pair<String, Int> = Pair("", 0)
    val memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnPostWriteDone.setOnClickListener {
            title = binding.edtPostWriteTitle.text.toString()
            detail = binding.edtPostWriteDetail.text.toString()

            if(title == null){
                showToastMessage("제목을 입력해주세요")
            }else if(location == 0 && subLocation == 0){
                showToastMessage("헬스 할 장소를 입력해주세요.")
            }else if(detail == null){
                showToastMessage("내용을 입력해주세요.")
            }else{
                RecruitService(this@RecruitWriteFragment)
                    .tryPostRecruitDetail(PostRecruitDetailRequest(detail!!,location,memberId,subLocation,title!!))
            }

        }

        binding.btnPostWriteLocation.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("지역 선택")
            builder.setItems(region.toTypedArray()) { _, position ->
                selectedRegion = region[position]
                selectedRegion?.let {
                    val subRegionItems = getRegionItems(it)
                    val subBuilder = AlertDialog.Builder(context)
                    subBuilder.setTitle("서브 지역 선택")
                    val subRegionNames = subRegionItems.map { it.first }.toTypedArray()
                    subBuilder.setItems(subRegionNames) { _, subPosition ->
                        selectedSubRegion = subRegionItems[subPosition]
                        // 선택한 지역 및 서브 지역에 대한 처리 로직 추가
                        updateLocation()
                    }

                    val subDialog = subBuilder.create()
                    // 사용자가 서브 지역을 선택하지 않고 대화 상자를 닫는 경우를 감지하고 필요한 작업을 수행합니다.
                    subDialog.setOnDismissListener {
                        if (selectedSubRegion == Pair("", 0)) {
                            selectedRegion = null
                            selectedSubRegion = Pair("", 0)
                            location = 0
                            subLocation = 0
                        }
                    }
                    subDialog.show()
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

    }

    private fun getRegionItems(region: String): Array<Pair<String, Int>> {
        return when (region) {
            "강원" -> arrayOf(
                "춘천,강촌" to 1, "경포대/사천" to 2, "영월/정선" to 3, "양양" to 4, "평창" to 5,
                "화천/철원/인제" to 6, "원주" to 7, "강릉역/교동" to 8, "속초/고성" to 9, "동해/삼척" to 10,
                "홍천/횡성" to 11
            )
            "인천" -> arrayOf(
                "부평" to 1, "서구" to 2, "주안" to 3, "인천공항" to 4, "강화" to 5,
                "남동구" to 6, "구월" to 7, "계양" to 8, "송도" to 9, "중구/월미도" to 10,
                "동암/간석" to 11, "용현/숭의/도화/동구" to 12
            )
            "서울" -> arrayOf(
                "강남/역삼/삼성" to 1, "잠실/신천" to 2, "신림/서울대" to 3, "화곡/까치산" to 4, "신촌/홍대" to 5,
                "종로/대학로" to 6, "동묘앞 역" to 7, "이태원/용산" to 8, "회기/고려대" to 9, "건대/군자" to 10,
                "수유/미아" to 11, "태릉/노원" to 12, "서초/신사" to 13, "영등포/여의도" to 14, "천호/길동" to 15,
                "구로/금천" to 16, "연신내/불광" to 17, "성신여대" to 18, "동대문" to 19, "장안동" to 20,
                "왕십리/성수" to 21, "상봉/중량" to 22
            )
            "경기" -> arrayOf(
                "수원/인계" to 1, "안양/평촌" to 2, "용인" to 3, "하남" to 4, "안산" to 5,
                "시흥" to 6, "평택" to 7, "일산/고양" to 8, "김포" to 9, "구리" to 10,
                "남양주" to 11, "양주/동두천/연천" to 12, "가평/창평" to 13, "수원역/구운/장안" to 14, "성남/분당/위례" to 15,
                "동탄/화성/오산" to 16, "안산/중앙역" to 17, "군포/의왕" to 18, "광명" to 19, "부천" to 20,
                "파주" to 21, "의정부" to 22, "남양주" to 23, "포천" to 24, "양평" to 25,
                "제부도/대부도" to 26
            )
            else -> arrayOf()
        }
    }

    fun updateLocation() {
        if (selectedRegion != null && selectedSubRegion != null) {
            val regionIndex = regionIndexes[selectedRegion]
            val subRegionIndex = selectedSubRegion.second
            Log.d("Location", "Region Index: $regionIndex, SubRegion Index: $subRegionIndex")

            // 선택한 지역 및 서브 지역에 대한 처리 로직 추가
            location = regionIndex ?: 0
            subLocation = subRegionIndex ?: 0

            // TextView 업데이트
            binding.tvPostWriteLocationCity.text = selectedRegion
            binding.tvPostWriteLocationDetail.text = selectedSubRegion.first
        }
    }

    private fun textMaintain(){
        binding.edtPostWriteTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출되는 콜백
                title = binding.edtPostWriteTitle.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edtPostWriteDetail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출되는 콜백
                title = binding.edtPostWriteDetail.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        parentFragmentManager.popBackStack()
    }


    override fun onPostRecruitDetailSuccess(response: PostRecruitDetailResponse) {
        if(response.code == 200){
            showToastMessage("글 작성 완료")
            parentFragmentManager.popBackStack()
        }

    }
    override fun onPostRecruitDetailFailure(message: String) {
        showToastMessage("오류 : $message")
    }

    override fun onGetRecruitAllSuccess(response: GetRecruitLocationResponse) {}
    override fun onGetRecruitAllFailure(message: String) {}
    override fun onGetRecruitLocationSuccess(response: GetRecruitLocationResponse) {}
    override fun onGetRecruitLocationFailure(message: String) {}

    override fun onGetRecruitDetailSuccess(response: GetRecruitDetailResponse) {}

    override fun onGetRecruitDetailFailure(message: String) {}



    override fun onPostRecruitChatSuccess(response: PostRecruitChatResponse) {}
    override fun onPostRecruitChatFailure(message: String) {}
}