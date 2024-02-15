package com.example.natube.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.editprofile.EditProfileValidExtension.includeEnglishKoreanNumber

class EditChannelActivityViewModel: ViewModel() {
    private var _nameErrorMessage: MutableLiveData<EditProfileErrorMessage> = MutableLiveData()
    val nameErrorMessage: LiveData<EditProfileErrorMessage> = _nameErrorMessage
    fun validateName(name: String) {
        _nameErrorMessage.value = when {
            name.isBlank() -> EditProfileErrorMessage.EMPTY_NAME
            name.includeEnglishKoreanNumber() -> EditProfileErrorMessage.NULL
            else -> EditProfileErrorMessage.INVALID_NAME
        }
    }





}