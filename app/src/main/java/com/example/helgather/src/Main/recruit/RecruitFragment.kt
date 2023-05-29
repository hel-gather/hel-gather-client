package com.example.helgather.src.Main.recruit

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentRecruitBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.recruit.models.GetRecruitAllResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse

class RecruitFragment : BaseFragment<FragmentRecruitBinding>(FragmentRecruitBinding::bind , R.layout.fragment_recruit),RecruitFragmentInterface {


    private val regionData = hashMapOf(
        "강원" to hashMapOf(
            "춘천,강촌" to 1, "경포대/사천" to 2, "영월/정선" to 3, "양양" to 4, "평창" to 5,
            "화천/철원/인제" to 6, "원주" to 7, "강릉역/교동" to 8, "속초/고성" to 9, "동해/삼척" to 10,
            "홍천/횡성" to 11
        ),
        "인천" to hashMapOf(
            "부평" to 1, "서구" to 2, "주안" to 3, "인천공항" to 4, "강화" to 5,
            "남동구" to 6, "구월" to 7, "계양" to 8, "송도" to 9, "중구월미도" to 10,
            "동암간석" to 11, "용현숭의도화동구" to 12
        ),
        "서울" to hashMapOf(
            "강남역삼삼성" to 1, "잠실신천" to 2, "신림서울대" to 3, "화곡까치산" to 4, "신촌홍대" to 5,
            "종로대학로" to 6, "동묘앞역" to 7, "이태원용산" to 8, "회기고려대" to 9, "건대군자" to 10,
            "수유미아" to 11, "태릉노원" to 12, "서초신사" to 13, "영등포여의도" to 14, "천호길동" to 15,
            "구로금천" to 16, "연신내불광" to 17, "성신여대" to 18, "동대문" to 19, "장안동" to 20,
            "왕십리성수" to 21, "상봉중량" to 22
        ),
        "경기" to hashMapOf(
            "수원인계" to 1, "안양평촌" to 2, "용인" to 3, "하남" to 4, "안산" to 5,
            "시흥" to 6, "평택" to 7, "일산고양" to 8, "김포" to 9, "구리" to 10,
            "남양주" to 11, "양주동두천연천" to 12, "가평창평" to 13, "수원역구운장안" to 14, "성남분당위례" to 15,
            "동탄화성오산" to 16, "안산중앙역" to 17, "군포의왕" to 18, "광명" to 19, "부천" to 20,
            "파주" to 21, "의정부" to 22, "남양주" to 23, "포천" to 24, "양평" to 25,
            "제부도대부도" to 26
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabPostWriting.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frm_main,RecruitWriteFragment()).addToBackStack("detail").commit()
        }

        RecruitService(this@RecruitFragment).tryGetRecruitAll()

        //스와이프시 게시물 갱신
        binding.srlPost.setOnRefreshListener {
            //여기서 서버랑 통신
        }
        
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }




    override fun onGetRecruitAllSuccess(response: GetRecruitAllResponse) {
        if(response.code == 200){
            binding.rvPost.apply {
                adapter = RecruitListAdapter(response.getRecruitAllResult)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        }
    }
    override fun onGetRecruitAllFailure(message: String) {
        showToastMessage("오류 : $message")
    }
    override fun onGetRecruitLocationSuccess(response: GetRecruitLocationResponse) {}
    override fun onGetRecruitLocationFailure(message: String) {}
    override fun onGetRecruitDetailSuccess(response: GetRecruitDetailResponse) {}
    override fun onGetRecruitDetailFailure(message: String) {}
    override fun onPostRecruitDetailSuccess(response: PostRecruitDetailResponse) {}
    override fun onPostRecruitDetailFailure(message: String) {}
    override fun onPostRecruitChatSuccess(response: PostRecruitChatResponse) {}
    override fun onPostRecruitChatFailure(message: String) {}
}