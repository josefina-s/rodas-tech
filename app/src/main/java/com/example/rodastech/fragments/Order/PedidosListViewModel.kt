package com.example.rodastech.fragments.Order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rodastech.entities.Cloth
import com.example.rodastech.entities.Pedido
import com.example.rodastech.entities.ProductoPedido
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


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

    private val _productosPedidosSeleccionados = MutableLiveData<MutableList<ProductoPedido>>()
    val productosPedidosSeleccionados: LiveData<MutableList<ProductoPedido>>
        get() = _productosPedidosSeleccionados

    val db = Firebase.firestore

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

    fun getAllPedidos(){
        viewModelScope.launch {
            val pedidos=pGetAllPedidos()
            _listaPedidos.value=pedidos
        }
    }
    suspend fun pGetAllPedidos(): MutableList<Pedido> {
        lateinit var dbPedidosList: MutableList<Pedido>
        dbPedidosList = mutableListOf()
        try {
            val data = db.collection("pedidos").get().await()
            for (pedido in data) {
                dbPedidosList.add(pedido.toObject(Pedido::class.java))
            }
        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN LIST PEDIDO VIEW MODEL ${e.message}")
        }
        return dbPedidosList
    }

    fun getProductosPedidos(pedido: Pedido){
        viewModelScope.launch {
            val pPedidos=pGetProductosSeleccionados(pedido)
            _productosPedidosSeleccionados.value=pPedidos
        }
    }
    suspend fun pGetProductosSeleccionados(pedido: Pedido): MutableList<ProductoPedido> {
        lateinit var dbProductosPedidosList: MutableList<ProductoPedido>
        dbProductosPedidosList = mutableListOf()
        try {
            val data = db.collection("productosPedidos")
                .whereEqualTo("id",pedido.idProductosPedidos)
                .get()
                .await()
            for (pPedidos in data) {
                dbProductosPedidosList.add(pPedidos.toObject(ProductoPedido::class.java))
            }
        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN LIST PEDIDO VIEW MODEL ${e.message}")
        }
        return dbProductosPedidosList
    }

}