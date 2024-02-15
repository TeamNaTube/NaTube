package com.example.natube.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.editprofile.EditProfileValidExtension.includeEnglishKoreanNumber

class EditChannelActivityViewModel: ViewModel() {
    private var _nameErrorMessage: MutableLiveData<EditProfileErrorMessage> = MutableLiveData()
    val nameErrorMessage: LiveData<EditProfileErrorMessage> = _nameErrorMessage 
    
    private var _name: MutableLiveData<String> = MutableLiveData()
    var nameVM: LiveData<String> = _name
    
    private var _description: MutableLiveData<String> = MutableLiveData()
    val descriptionVM: LiveData<String> = _description
    fun validateName(name1: String) {
        _name.value = name1
        _nameErrorMessage.value = when {
            name1.isBlank() -> EditProfileErrorMessage.EMPTY_NAME
            name1.includeEnglishKoreanNumber() -> EditProfileErrorMessage.NULL
            else -> EditProfileErrorMessage.INVALID_NAME
        }
    }

    fun setDescription(description1: String) {
        _description.value = description1

    }


}