package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductoPedido(
    var nombre: String?="",
    var metros: Int=0,
    var idPedido : String?="",
    var idCloth : String?=""
) : Parcelable {
}