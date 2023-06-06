package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pedido(
    var cliente: String?="",
    var fecha: String?="",
    var idProductosPedidos : String?=""

) : Parcelable {
}