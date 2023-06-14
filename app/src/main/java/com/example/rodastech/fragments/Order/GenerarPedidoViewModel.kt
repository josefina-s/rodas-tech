package com.example.rodastech.fragments.Order


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.example.rodastech.entities.Pedido
import com.example.rodastech.entities.ProductoPedido
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

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

    suspend fun insertPedido(pedido: Pedido) = coroutineScope {
        try {
            val insertMap = mapOf(
                "cliente" to pedido.cliente,
                "fecha" to pedido.fecha,
                "idProductosPedidos" to pedido.idProductosPedidos
            )
            db.collection("pedidos").add(insertMap).await()

        } catch (e: Exception) {
            Log.d("RODASTECH", "EXCEPTION EN CREATE CLOTH VIEW MODEL ${e.message}")
        }
    }

    suspend fun insertProductosPedidos(productosPedidos: MutableList<ProductoPedido>, id: String) =
        coroutineScope {
            try {
                for (p in productosPedidos) {
                    val insertMap = mapOf(
                        "nombre" to p.nombre,
                        "metros" to p.metros,
                        "id" to id,
                        "idCloth" to p.idCloth
                    )
                    db.collection("productosPedidos").add(insertMap).await()
                    updateStockProductoPedido(p.idCloth!!,p.metros)
                }

            } catch (e: Exception) {
                Log.d("RODASTECH", "EXCEPTION EN CREATE CLOTH VIEW MODEL ${e.message}")
            }
        }

    suspend fun updateStockProductoPedido(idCloth : String, metrosPedidos:Int) =
        coroutineScope {
            try {
                    val clothQuery =
                        db.collection("cloths").whereEqualTo("id", idCloth)
                            .get()
                            .await()
                    for (dbCloth in clothQuery) {
                        var resultStock= dbCloth.toObject(Cloth::class.java).stockActual?.minus(metrosPedidos)
                        val updateMap = mapOf(
                            "stockActual" to  resultStock
                        )
                        db.collection("cloths").document(dbCloth.id)
                            .update(updateMap)
                            .await()
                    }
            } catch (e: Exception) {
                Log.d("RODASTECH", "EXCEPTION EN updateStockProductosPedidos ${e.message}")
            }
        }

}