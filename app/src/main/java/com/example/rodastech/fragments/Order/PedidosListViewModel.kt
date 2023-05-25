package com.example.rodastech.fragments.Order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Pedido


class PedidosListViewModel : ViewModel() {
    private val _nuevoPedido = MutableLiveData<Pedido>()
    val nuevoPedido: LiveData<Pedido>
        get() = _nuevoPedido

    private val _pedidoSeleccionado = MutableLiveData<Pedido>()
    val pedidoSeleccionado: LiveData<Pedido>
        get() = _pedidoSeleccionado

    private val _listaPedidos = MutableLiveData<MutableList<Pedido>>()
    val listaPedidos: LiveData<MutableList<Pedido>>
        get() = _listaPedidos

    fun agregarPedido(pedido: Pedido) {
        _nuevoPedido.value = pedido
        agregarPedidoALista(pedido)
    }

    fun agregarPedidoALista(pedido: Pedido) {
        val pedidosActuales = _listaPedidos.value.orEmpty().toMutableList()
        pedidosActuales.add(pedido)
        _listaPedidos.value = pedidosActuales
    }

    fun seleccionarPedido(pedido: Pedido) {
        _pedidoSeleccionado.value = pedido
    }
}