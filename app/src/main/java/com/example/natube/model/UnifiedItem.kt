package com.example.natube.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UnifiedItem(
    val videoTitle: String,
    val channelTitle: String,
    val description: String,
    val dateTime: String,
    val thumbnailsUrl : String,
    val categoryId: String
): Parcelable {

    // 항목이 '좋아요' 상태인지 나타내는 변수. 기본값은 false.
    var isLike = false

}