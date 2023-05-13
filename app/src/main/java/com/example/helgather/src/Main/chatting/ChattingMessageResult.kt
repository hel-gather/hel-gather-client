package com.example.helgather.src.Main.chatting

data class ChattingMessageResult(
    var userId : Int, //유저 아이디
    var userProfile : Int, //유저 프로필 사진
    var message : String, //메세지
    var time : String, // 언제
    val isFirst : Boolean //첫번째 메세지 인지
)