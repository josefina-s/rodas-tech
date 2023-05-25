package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductoPedido(
    private var _nombre: String,
    private var _metros: String
) : Parcelable {
    var nombre: String
        get() = _nombre
        set(value) {
            _nombre = value
        }

    var metros: String
        get() = _metros
        set(value) {
            _metros = value
        }

    override fun toString(): String {
        return nombre
    }
}