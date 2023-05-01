package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cloth(
    var id:Int,
    var name: String,
    var meters:String,
    var price:Int
): Parcelable {
}