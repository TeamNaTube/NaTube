package com.example.natube.ui.home

sealed class HomeWidget {
    data class Title(val title:String): HomeWidget()
}