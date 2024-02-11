package com.example.natube.ui.myvideo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyChannel(
    val myChannelName: String?,
    val myProfilePicture: Int?,
    val myBackgroundPicture: Int?,
    val myChannelDescription: String?
) : Parcelable
