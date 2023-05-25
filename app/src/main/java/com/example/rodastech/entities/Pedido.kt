package com.example.rodastech.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pedido(
    val _cliente: String,
    val _fecha: String,
    val _productos: MutableList<ProductoPedido>
) : Parcelable {
    var cliente: String
        get() = _cliente
        set(value) {
            _cliente
        }

    var fecha: String
        get() = _fecha
        set(value) {
            _fecha
        }

    var productos: MutableList<ProductoPedido>
        get() = _productos
        set(value) {
            _productos
        }
}
