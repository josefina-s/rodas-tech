package com.example.rodastech.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.entities.Pedido

class PedidoAdapter(
    var pedidos: MutableList<Pedido>,
    var onClick: (Int) -> Unit
) : RecyclerView.Adapter<PedidoAdapter.PedidoHolder>() {

    class PedidoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setClienteName(name: String) {
            val txtName: TextView = view.findViewById(R.id.txtClientePedido)
            txtName.text = name
        }

        fun setFecha(fecha: String){
            val txtFechaPedido: TextView = view.findViewById(R.id.txtFechaPedido)
            txtFechaPedido.text = fecha
        }

        fun getCard() : CardView {
            return view.findViewById(R.id.cardPedido)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_item, parent, false)
        return (PedidoHolder(view))
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    override fun onBindViewHolder(holder: PedidoHolder, position: Int) {
        pedidos[position].cliente?.let { holder.setClienteName(it) }
        pedidos[position].fecha?.let { holder.setFecha(it) }
        holder.getCard().setOnClickListener {
            onClick(position)
        }
    }

    fun actualizarPedidos(pedidosActualizados : MutableList<Pedido>) {
        pedidos = pedidosActualizados
        notifyDataSetChanged()
        Log.d("josie_test", "Cant pedidos en la lista: ${pedidos.size}")
    }

}