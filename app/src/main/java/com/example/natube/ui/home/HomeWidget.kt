package com.example.natube.ui.home

import com.example.natube.model.Category

sealed class HomeWidget {
    data class TitleWidget(val title:String): HomeWidget()
    data class CategoryWidget(val mCategories : List<Category>) : HomeWidget()

}