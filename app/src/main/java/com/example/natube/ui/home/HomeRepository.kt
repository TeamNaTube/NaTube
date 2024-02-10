package com.example.natube.ui.home

import android.content.SharedPreferences
import com.example.natube.model.Chip
import com.google.gson.Gson

class HomeRepository(appData: AppData) {
    companion object {
        const val PREF_CATEGORY = "pref_category"
        const val PREF_KEYWORD = "pref_keyword"
    }

    private val prefCategory: SharedPreferences = appData.prefCategory
    private val prefKeyword: SharedPreferences = appData.prefKeyword

    //1. 데이터 받기

    fun updatePrefCategory(categoryList: List<Chip>) {
        val pref = prefCategory
        val edit = pref.edit()

        edit.clear()
        categoryList.forEach { chip ->
            val json = Gson().toJson(chip)
            edit.putString(chip.categoryId, json)
        }
        edit.apply()
    }

    fun updatePrefKeyword(keywordList: List<Chip>) {
        val pref = prefKeyword
        val edit = pref.edit()

        edit.clear()
        keywordList.forEach { chip ->
            val json = Gson().toJson(chip)
            edit.putString(chip.name, json)
        }
        edit.apply()

    }

    //2. 데이터 내보내기
    fun getCategoryList(): List<Chip> {
        if (isEmptyList()) return emptyList()

        val pref = prefCategory
        val values = pref.all.values

        val categoryList = mutableListOf<Chip>()
        values.forEach {
            categoryList.add(Gson().fromJson(it.toString(), Chip::class.java))
        }

        return categoryList
    }

    fun getKeywordList(): List<Chip> {
        if (isEmptyList()) return emptyList()

        val pref = prefKeyword
        val values = pref.all.values

        val keywordList = mutableListOf<Chip>()
        values.forEach {
            keywordList.add(Gson().fromJson(it.toString(), Chip::class.java))
        }

        return keywordList
    }

    //3. 데이터 있는지 확인
    fun isEmptyList(): Boolean = prefCategory.all.isEmpty() || prefKeyword.all.isEmpty()

    fun clearList(){
        prefCategory.edit().clear().apply()
        prefKeyword.edit().clear().apply()
    }
}