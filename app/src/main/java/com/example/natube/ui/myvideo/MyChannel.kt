package com.example.natube.ui.myvideo

import android.net.Uri
import android.os.Parcelable
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize


data class MyChannel(
    val myChannelName: String?,
    val myProfilePicture: GsonBuilder?,
    val myBackgroundPicture: GsonBuilder?,
    var myChannelDescription: String?
)
