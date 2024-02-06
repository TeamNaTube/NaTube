package com.example.natube.model.searchmodel

data class SearchModel(
    val etag: String,
    val items: List<Item>, //검색된 동영상 목롤들
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)