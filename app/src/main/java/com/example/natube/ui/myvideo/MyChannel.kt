package com.example.natube.ui.myvideo

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyChannel(
    val myChannelName: String?,
    var myChannelDescription: String?,
) : Parcelable
