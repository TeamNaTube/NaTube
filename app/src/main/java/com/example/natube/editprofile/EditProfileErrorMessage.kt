package com.example.natube.editprofile

import androidx.annotation.StringRes
import com.example.natube.R

enum class EditProfileErrorMessage(
    @StringRes val message: Int,
) {
    EMPTY_NAME(R.string.empty_name_message),
    INVALID_NAME(R.string.invalid_name_message),
    EMPTY_DESCRIPTION(R.string.empty_description_message),
    NULL(R.string.verified),
}