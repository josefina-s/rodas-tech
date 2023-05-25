package com.example.rodastech.entities


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Client (
    var id:String?="",
    var name: String?="",
    var cuit: String?="",
    var contactPerson: String?="",
    var telephone: Int?=0,
    var email: String?="",
    var address: String?="",
    var cp:Int?=0
        ): Parcelable{
}