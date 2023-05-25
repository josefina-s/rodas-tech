package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cloth( //mheredia 04052023 igualo campos al figma
    var id:String?="",
    var name: String?="",
    var description: String?="",
    var provider: String?="",
    var color: String?="",
    var width: Int?=0,
    var long: Int?=0,
//    var meters:Int?=0,
    var price:Int?=0,
    var stockActual:Int?=0,
    var stockMinimo:Int?=0
): Parcelable {
}