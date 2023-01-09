package com.example.daggerhilt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String,
    val body: String
): Parcelable