package com.example.applemarket

import android.os.Parcelable
import kotlinx.android.parcel.*

@Parcelize
data class Item(
    val image: Int,
    val name: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    val likes: Int,
    val chats: Int
) : Parcelable