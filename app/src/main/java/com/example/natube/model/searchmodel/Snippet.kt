package com.example.natube.model.searchmodel


data class Snippet(
    val channelId: String,
    val channelTitle: String, // 채널 명
    val description: String, // 동영상 설명
    val liveBroadcastContent: String,
    val publishTime: String,
    val publishedAt: String,  // 채널 등록일
    val thumbnails: Thumbnails, // 동영상 썸네일들
    val title: String, //동영상 이름
)