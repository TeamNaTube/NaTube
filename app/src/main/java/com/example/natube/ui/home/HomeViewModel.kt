package com.example.natube.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.model.Category

class HomeViewModel : ViewModel() {
    private var _mCategoryList = MutableLiveData<MutableList<Category>>()
    val mCategoryList: LiveData<MutableList<Category>> get() = _mCategoryList

    init{
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

    fun setSelectedItemPosition(position: Int){
        val newList = mCategoryList.value?.toMutableList()!!
        for(idx in newList.indices){
            newList[idx].isClicked = idx == position
        }
        _mCategoryList.value = newList
    }

}