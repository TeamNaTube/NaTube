package com.example.natube

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.model.UnifiedItem

class VideoDetailActivityViewModel : ViewModel() {

    private var _item = MutableLiveData<UnifiedItem?>()

    val item : LiveData<UnifiedItem?> = _item
    fun removeLike(itemDetail: UnifiedItem) {
        Log.d("HappyDetailVM", "^^ removeLike ${itemDetail.isLike}")
        itemDetail.isLike = false
        Log.d("HappyDetailVM", "^^ removeLike ${itemDetail.isLike} changed?")

        _item.value = itemDetail
    }

    fun addLike(itemDetail: UnifiedItem) {
        Log.d("HappyDetailVM", "^^ addLike ${itemDetail.isLike}")
        itemDetail.isLike = true
        Log.d("HappyDetailVM", "^^ addLike ${itemDetail.isLike} changed?")
        _item.value = itemDetail
    }




}