package com.example.helgather.src.Main.recruit

import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResult
import com.example.helgather.src.Main.recruit.models.PostRecruitChatRequest
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailRequest
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecruitService(val recruitFragmentInterface: RecruitFragmentInterface) {

    fun tryGetRecruitAll() {
        val recruitRetrofitInterface = ApplicationClass.sRetrofit.create(RecruitRetrofitInterface::class.java)
        recruitRetrofitInterface.getRecruitAll().enqueue(object : Callback<GetRecruitLocationResponse> {
            override fun onResponse(call: Call<GetRecruitLocationResponse>, response: Response<GetRecruitLocationResponse>) {
                if (response.isSuccessful) {
                    recruitFragmentInterface.onGetRecruitAllSuccess(response.body() as GetRecruitLocationResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, GetRecruitLocationResponse::class.java)
                    val getRecruitLocationResult = errorResponse.getRecruitLocationResult
                    val getRecruitLocationResponse = GetRecruitLocationResponse(false, errorResponse.code, errorResponse.message, getRecruitLocationResult)
                    recruitFragmentInterface.onGetRecruitAllSuccess(getRecruitLocationResponse)
                }
            }

            override fun onFailure(call: Call<GetRecruitLocationResponse>, t: Throwable) {
                recruitFragmentInterface.onGetRecruitAllFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetRecruitLocation(location: Int, subLocation: Int) {
        val recruitRetrofitInterface = ApplicationClass.sRetrofit.create(RecruitRetrofitInterface::class.java)
        recruitRetrofitInterface.getRecruitLocation(location, subLocation).enqueue(object : Callback<GetRecruitLocationResponse> {
            override fun onResponse(call: Call<GetRecruitLocationResponse>, response: Response<GetRecruitLocationResponse>) {
                if (response.isSuccessful) {
                    recruitFragmentInterface.onGetRecruitLocationSuccess(response.body() as GetRecruitLocationResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, GetRecruitLocationResponse::class.java)
                    val getRecruitLocationResult = errorResponse.getRecruitLocationResult
                    val getRecruitLocationResponse = GetRecruitLocationResponse(false, errorResponse.code, errorResponse.message, getRecruitLocationResult)
                    recruitFragmentInterface.onGetRecruitLocationSuccess(getRecruitLocationResponse)
                }
            }

            override fun onFailure(call: Call<GetRecruitLocationResponse>, t: Throwable) {
                recruitFragmentInterface.onGetRecruitLocationFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetRecruitDetail(id: Int) {
        val recruitRetrofitInterface = ApplicationClass.sRetrofit.create(RecruitRetrofitInterface::class.java)
        recruitRetrofitInterface.getRecruitDetail(id).enqueue(object : Callback<GetRecruitDetailResponse> {
            override fun onResponse(call: Call<GetRecruitDetailResponse>, response: Response<GetRecruitDetailResponse>) {
                if (response.isSuccessful) {
                    recruitFragmentInterface.onGetRecruitDetailSuccess(response.body() as GetRecruitDetailResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, GetRecruitDetailResponse::class.java)
                    val getRecruitDetailResult = errorResponse.getRecruitDetailResult
                    val getRecruitDetailResponse = GetRecruitDetailResponse(false, errorResponse.code, errorResponse.message, getRecruitDetailResult)
                    recruitFragmentInterface.onGetRecruitDetailSuccess(getRecruitDetailResponse)
                }
            }

            override fun onFailure(call: Call<GetRecruitDetailResponse>, t: Throwable) {
                recruitFragmentInterface.onGetRecruitDetailFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryPostRecruitDetail(request: PostRecruitDetailRequest) {
        val recruitRetrofitInterface = ApplicationClass.sRetrofit.create(RecruitRetrofitInterface::class.java)
        recruitRetrofitInterface.postRecruitDetail(request).enqueue(object : Callback<PostRecruitDetailResponse> {
            override fun onResponse(call: Call<PostRecruitDetailResponse>, response: Response<PostRecruitDetailResponse>) {
                if (response.isSuccessful) {
                    recruitFragmentInterface.onPostRecruitDetailSuccess(response.body() as PostRecruitDetailResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, PostRecruitDetailResponse::class.java)
                    val postRecruitDetailResult = errorResponse.result
                    val postRecruitDetailResponse = PostRecruitDetailResponse(false, errorResponse.code, errorResponse.message, postRecruitDetailResult)
                    recruitFragmentInterface.onPostRecruitDetailSuccess(postRecruitDetailResponse)
                }
            }

            override fun onFailure(call: Call<PostRecruitDetailResponse>, t: Throwable) {
                recruitFragmentInterface.onPostRecruitDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostRecruitChat(id: Int, request: PostRecruitChatRequest) {
        val recruitRetrofitInterface = ApplicationClass.sRetrofit.create(RecruitRetrofitInterface::class.java)
        recruitRetrofitInterface.postRecruitChat(id, request).enqueue(object : Callback<PostRecruitChatResponse> {
            override fun onResponse(call: Call<PostRecruitChatResponse>, response: Response<PostRecruitChatResponse>) {
                if (response.isSuccessful) {
                    recruitFragmentInterface.onPostRecruitChatSuccess(response.body() as PostRecruitChatResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, PostRecruitChatResponse::class.java)
                    val postRecruitChatResult = errorResponse.postRecruitChatResult
                    val postRecruitChatResponse = PostRecruitChatResponse(false, errorResponse.code, errorResponse.message, postRecruitChatResult)
                    recruitFragmentInterface.onPostRecruitChatSuccess(postRecruitChatResponse)
                }
            }

            override fun onFailure(call: Call<PostRecruitChatResponse>, t: Throwable) {
                recruitFragmentInterface.onPostRecruitChatFailure(t.message ?: "통신 오류")
            }
        })
    }
}
