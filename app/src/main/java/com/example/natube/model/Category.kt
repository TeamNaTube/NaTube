package com.example.natube.model

data class Category(
    val categoryId: String,
    var name: String? = null,
    var isClicked: Boolean = false,
) {
    companion object {
        val categoryMap = mapOf(
            "0" to "Empty",
            "1" to "영화/애니메이션",
            "2" to "자동차/차량",
            "10" to "음악",
            "15" to "애완동물과 동물",
            "18" to "단편 영화",
            "19" to "여행",
            "20" to "게임",
            "21" to "VLOG",
            "22" to "피플 & 블로그",
            "23" to "코미디",
            "24" to "예능",
            "25" to "뉴스/정치",
            "26" to "패션",
            "27" to "교육",
            "28" to "과학/기술",
            "29" to "비영리 & 행동주의",
            "30" to "영화",
            "31" to "애니메이션",
            "32" to "액션/어드벤처",
            "33" to "클래식",
            "34" to "코미디",
            "35" to "다큐멘터리",
            "36" to "드라마",
            "37" to "가족",
            "38" to "외국인",
            "39" to "호러",
            "40" to "공상과학/판타지",
            "41" to "스릴러",
            "42" to "쇼츠",
            "43" to "쇼 프로그램",
            "44" to "트레일러",
        )
    }

    init {
        name = categoryMap[categoryId].toString()
    }
}