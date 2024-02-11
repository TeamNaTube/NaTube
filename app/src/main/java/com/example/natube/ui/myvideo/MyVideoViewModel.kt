package com.example.natube.ui.myvideo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.model.UnifiedItem

class MyVideoViewModel : ViewModel() {


    private var _selectedItem = MutableLiveData<UnifiedItem?>()

    val selectedItem : LiveData<UnifiedItem?> = _selectedItem

    fun getSelectedItem(item: UnifiedItem?) {
        val chosenItem: UnifiedItem? = item?.copy()
        _selectedItem.value = chosenItem
    }
}