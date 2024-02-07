package com.example.natube.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hwangtube.network.RetrofitInstance
import com.example.natube.model.Chip
import com.example.natube.model.UnifiedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var _mCategoryList = MutableLiveData<List<Chip>>(listOf())
    val mCategoryList : LiveData<List<Chip>> get() = _mCategoryList

    private var _mSelectedCategoryList = MutableLiveData<List<Chip>>(listOf())
    val mSelectedCategoryList: LiveData<List<Chip>> get() = _mSelectedCategoryList

    private var selectedCategoryId = "1"

    private var _mItemByCategoryList = MutableLiveData<List<UnifiedItem>>(listOf())
    val mItemByCategoryList: LiveData<List<UnifiedItem>> get() = _mItemByCategoryList

    init {
        initCategoryList()
    }

    private fun initCategoryList() {
        val list = mutableListOf<Chip>()
        Chip.categoryMap.forEach { map ->
            list.add(Chip(categoryId = map.key, name = map.value))
        }

        _mCategoryList.value = list

//        _mSelectedCategoryList.value = list
    }

    fun isClickedItem(position: Int){
        val list = _mCategoryList.value ?: listOf()
        list[position].isClicked = !list[position].isClicked
        _mCategoryList.value = list
    }

    /**
     *  카테고리 선택 부분
     */

    fun setSelectedItemPosition(position: Int) {
        val newList = mSelectedCategoryList.value!!
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        selectedCategoryId = newList[position].categoryId
        _mSelectedCategoryList.value = newList
    }

    fun fetchSearchVideoByCategory() {
        viewModelScope.launch {
            val unifiedItems = searchVideoByCategory()
            _mItemByCategoryList.value = unifiedItems
        }
    }

    private suspend fun searchVideoByCategory() = withContext(Dispatchers.IO) {
        val items =
            RetrofitInstance.api.getTrendingVideos(videoCategoryId = selectedCategoryId).items
        val unifiedItems = mutableListOf<UnifiedItem>()
        items.forEach { item ->
            unifiedItems.add(item.toUnifiedItem())
        }
        unifiedItems
    }

}