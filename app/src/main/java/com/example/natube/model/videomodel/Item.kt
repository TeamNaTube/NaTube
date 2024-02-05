package com.example.natube.model.videomodel

import com.example.natube.model.UnifiedItem

data class Item(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet // 비디오 의 기본 정보가 저장
){
    fun toUnifiedItem(): UnifiedItem = UnifiedItem(
        videoTitle = snippet.title,
        channelTitle = snippet.channelTitle,
        description = snippet.description,
        dateTime = snippet.publishedAt,
        thumbnailsUrl = snippet.thumbnails.high.url,
        categoryId = snippet.categoryId,
    )

}
