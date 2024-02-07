package com.example.natube.model

data class Chip(
    val categoryId: String,
    var name: String? = null,
    var isClicked: Boolean = false,
) {
    companion object {
        val categoryMap = mapOf(
            "1" to "영화(애니메이션)",
            "2" to "자동차(차량)",
            "10" to "음악",
            "15" to "애완동물",
            "17" to "스포츠",
            "20" to "게임",
            "22" to "사람/브이로그",
            "23" to "코미디",
            "24" to "예능",
            "25" to "뉴스/정치",
            "26" to "패션",
            "28" to "과학/기술"
        )
    }

    init {
        if (name == null) name = categoryMap[categoryId].toString()
    }
}