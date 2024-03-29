package com.example.natube.ui.home

import com.example.natube.model.Chip
import com.example.natube.model.UnifiedItem

sealed class HomeWidget {
    data class TitleWidget(val title:String): HomeWidget()
    data class ChipWidget(val mCategories : List<Chip>) : HomeWidget()
    data class ListCategoryVideoItemWidget(val mUnifiedItems: List<UnifiedItem>) : HomeWidget()
    data class ListKeywordVideoItemWidget(val mUnifiedItems: List<UnifiedItem>) : HomeWidget()

}