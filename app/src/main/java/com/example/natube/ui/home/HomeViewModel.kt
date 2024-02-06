package com.example.natube.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hwangtube.network.RetrofitInstance
import com.example.natube.model.Category
import com.example.natube.model.UnifiedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private var _mCategoryList = MutableLiveData<List<Category>>(listOf())
    val mCategoryList: LiveData<List<Category>> get() = _mCategoryList

    private var selectedCategoryId = "1"

    private var _mUnifiedItemList = MutableLiveData<List<UnifiedItem>>(listOf())
    val mUnifiedItemList: LiveData<List<UnifiedItem>> get() = _mUnifiedItemList

    init {
        initCategoryList()
    }

    private fun initCategoryList() {
        val list = mutableListOf<Category>()
        list.add(Category("1", isClicked = true))
        list.add(Category("2"))
        list.add(Category("10"))
        list.add(Category("15"))
        list.add(Category("17"))
        _mCategoryList.value = list
    }

    fun setSelectedItemPosition(position: Int) {
        val newList = mCategoryList.value!!
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        selectedCategoryId = newList[position].categoryId
        _mCategoryList.value = newList
    }

    fun fetchSearchVideoByCategory() {
        viewModelScope.launch{
            val unifiedItems = searchVideoByCategory()
            _mUnifiedItemList.value = unifiedItems
        }
    }
    private suspend fun searchVideoByCategory()= withContext(Dispatchers.IO){
        val items = RetrofitInstance.api.getTrendingVideos(videoCategoryId = selectedCategoryId).items
        val unifiedItems = mutableListOf<UnifiedItem>()
        items.forEach {item->
            unifiedItems.add(item.toUnifiedItem())
        }
        unifiedItems
    }

}