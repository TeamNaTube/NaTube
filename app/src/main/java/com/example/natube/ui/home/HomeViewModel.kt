package com.example.natube.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hwangtube.network.RetrofitInstance
import com.example.natube.model.Category
import com.example.natube.model.UnifiedItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _mCategoryList = MutableLiveData<List<Category>>()
    val mCategoryList: LiveData<List<Category>> get() = _mCategoryList

    private var _mUnifiedItemList = MutableLiveData<List<UnifiedItem>>()
    val mUnifiedItemList: LiveData<List<UnifiedItem>> get() = _mUnifiedItemList

    init {
        initCategoryList()
    }

    private fun initCategoryList() {
        val list = mutableListOf<Category>()
        list.add(Category("31", isClicked = true))
        list.add(Category("32"))
        list.add(Category("33"))
        list.add(Category("34"))
        list.add(Category("35"))
        _mCategoryList.value = list
    }

    fun setSelectedItemPosition(position: Int) {
        val newList = mCategoryList.value!!
        for (idx in newList.indices) {
            newList[idx].isClicked = idx == position
        }
        _mCategoryList.value = newList
    }

    fun searchVideoByCategory(categoryId: String) {
        CoroutineScope(Dispatchers.IO).launch{
            val items = RetrofitInstance.api.getTrendingVideos().items
            items.forEach{ item->

            }
        }
    }

}