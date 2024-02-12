package com.example.natube.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale


@Parcelize
data class UnifiedItem(
    val videoTitle: String,
    val channelTitle: String,
    val description: String,
    var dateTime: String,
    val thumbnailsUrl : String,
    val categoryId: String
): Parcelable {
    // 항목이 '좋아요' 상태인지 나타내는 변수. 기본값은 false.
    var isLike = false
    init{
        dateTimeParsing()
    }

    //데이트 형식 파싱
    private fun dateTimeParsing(){
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA)
        val outputFormat = SimpleDateFormat("yyyy/MM/dd'\n'HH:mm:ss", Locale.KOREA)

        val inputDate = inputFormat.parse(dateTime)
        dateTime = outputFormat.format(inputDate)
    }
}