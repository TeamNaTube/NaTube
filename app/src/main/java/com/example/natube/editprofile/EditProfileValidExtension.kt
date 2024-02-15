package com.example.natube.editprofile

object EditProfileValidExtension {
    /*
* 소문자 & 숫자 포함
*/
    fun String.includeAlphabetAndNumber() =
        Regex("^[a-z0-9]*$").containsMatchIn(this)

    /*
    * 한글 이름
    */
    fun String.includeKorean() =
        Regex("^[가-힣]*$").containsMatchIn(this)

    fun String.includeEnglishKoreanNumber() =
        Regex("[ㄱ-ㅎ가-힣a-zA-Z0-9]+").containsMatchIn(this)
    /*
    * 특문 포함
    */
    fun String.includeSpecialCharacters() =
        Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+").containsMatchIn(this)

    /*
    * 대문자 포함
    */
    fun String.includeUpperCase() = Regex("[A-Z]").containsMatchIn(this)
}