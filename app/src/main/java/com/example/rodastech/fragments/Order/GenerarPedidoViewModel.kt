package com.example.rodastech.fragments.Order


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.ProductoPedido
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenerarPedidoViewModel : ViewModel() {
    val db = Firebase.firestore
    private val _listaProductos = MutableLiveData<MutableList<ProductoPedido>>()
    val listaProductos: LiveData<MutableList<ProductoPedido>> = _listaProductos

    private val _nuevoProducto = MutableLiveData<ProductoPedido>()
    val nuevoProducto: LiveData<ProductoPedido>
        get() = _nuevoProducto

    private val _clienteSeleccionado = MutableLiveData<Client?>()
    val clienteSeleccionado: MutableLiveData<Client?>
        get() = _clienteSeleccionado

    fun agregarProducto(producto: ProductoPedido) {
        _nuevoProducto.value = producto
    }

    fun agregarProductoALista(producto: ProductoPedido) {
        val productosActuales = _listaProductos.value.orEmpty().toMutableList()
        productosActuales.add(producto)
        _listaProductos.value = productosActuales
    }

    fun setClienteSeleccionado(cliente: Client) {
        _clienteSeleccionado.value = cliente
    }

    fun limpiarPedido() {
        _clienteSeleccionado.value = null
        _listaProductos.value = mutableListOf()
    }


}