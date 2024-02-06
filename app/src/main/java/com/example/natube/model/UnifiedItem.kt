package com.example.natube.model



data class UnifiedItem(
    val videoTitle: String,
    val channelTitle: String,
    val description: String,
    val dateTime: String,
    val thumbnailsUrl : String,
    val categoryId: String
)