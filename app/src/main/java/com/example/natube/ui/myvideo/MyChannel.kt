package com.example.natube.ui.myvideo

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyChannel(
    val myChannelName: String?,
    val myProfilePicture: Uri?,
    val myBackgroundPicture: Uri?,
    var myChannelDescription: String?
) : Parcelable
