package com.example.natube.model.videomodel

data class Snippet(
    val categoryId: String, // 카테고리 Id
    val channelId: String,
    val channelTitle: String, // 채널명
    val defaultAudioLanguage: String,
    val defaultLanguage: String,
    val description: String, // 동영상 설명
    val liveBroadcastContent: String,
    val localized: Localized,
    val publishedAt: String, // 채널 등록일
    val tags: List<String>,
    val thumbnails: Thumbnails, // 채널 썸네일
    val title: String // 동영상 이름
)