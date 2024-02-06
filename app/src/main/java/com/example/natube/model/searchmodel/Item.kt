package com.example.natube.model.searchmodel

import com.example.natube.model.UnifiedItem

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet // 아이템의 동영상 기본 정보들
){
    fun toUnifiedItem(): UnifiedItem = UnifiedItem(
        videoTitle = snippet.title,
        channelTitle = snippet.channelTitle,
        description = snippet.description,
        dateTime = snippet.publishedAt,
        thumbnailsUrl = snippet.thumbnails.high.url,
        categoryId = "0"
    )

}