package com.example.natube.ui.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

data class AppData(val application: Application){
    val prefCategory : SharedPreferences =
        application.getSharedPreferences(HomeRepository.PREF_CATEGORY, Context.MODE_PRIVATE)
    val prefKeyword : SharedPreferences =
        application.getSharedPreferences(HomeRepository.PREF_KEYWORD, Context.MODE_PRIVATE)

}
