package com.example.rodastech.entities


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Client (
    var _id:String?="",
    var _name: String?="",
    var _cuit: String?="",
    var _contactPerson: String?="",
    var _telephone: Int?=0,
    var _email: String?="",
    var _address: String?="",
    var _cp:Int?=0
): Parcelable{
    var id: String?
        get() = _id
        set(value) {
            _id = value
        }

    var name: String?
        get() = _name
        set(value) {
            _name = value
        }

    var cuit: String?
        get() = _cuit
        set(value) {
            _cuit = value
        }

    var contactPerson: String?
        get() = _contactPerson
        set(value) {
            _contactPerson = value
        }

    var telephone: Int?
        get() = _telephone
        set(value) {
            _telephone = value
        }

    var email: String?
        get() = _email
        set(value) {
            _email = value
        }

    var address: String?
        get() = _address
        set(value) {
            _address = value
        }

    var cp: Int?
        get() = _cp
        set(value) {
            _cp = value
        }

    override fun toString(): String {
        return name ?: ""
    }
}