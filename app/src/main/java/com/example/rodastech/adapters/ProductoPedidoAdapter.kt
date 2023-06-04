package com.example.rodastech.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.entities.ProductoPedido

class ProductoPedidoAdapter (
    var productos: MutableList<ProductoPedido>
) : RecyclerView.Adapter<ProductoPedidoAdapter.ProductoPedidoHolder>() {

    class ProductoPedidoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setProductoName(name: String) {
            val txtName: TextView = view.findViewById(R.id.txtDialog)
            txtName.text = name
        }

        fun setMetros(metros: Int){
            val txtMetros: TextView = view.findViewById(R.id.txtMetros)
            txtMetros.text = metros.toString() + " metros"
        }

        fun getCard() : CardView {
            return view.findViewById(R.id.cardPedido)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoPedidoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_pedido_item, parent, false)
        return (ProductoPedidoHolder(view))
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: ProductoPedidoHolder, position: Int) {
        productos[position].nombre?.let { holder.setProductoName(it) }
        holder.setMetros(productos[position].metros)

    }
    fun actualizarProductos(productosActualizados : MutableList<ProductoPedido>) {
        productos = productosActualizados
        notifyDataSetChanged()
        Log.d("josie_test", "Cant prod en la lista: ${productos.size}")
    }

    fun clearProductos() {
        productos.clear()
        notifyDataSetChanged()
    }

}