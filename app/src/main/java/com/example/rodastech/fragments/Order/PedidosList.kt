package com.example.rodastech.fragments.Order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.PedidoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PedidosList : Fragment() {

    lateinit var v: View
    lateinit var recyclerPedidos: RecyclerView
    lateinit var floatingButton: FloatingActionButton

    private val pedidosViewModel: PedidosListViewModel by activityViewModels()

    lateinit var adapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pedidos_list, container, false)
        recyclerPedidos = v.findViewById(R.id.RecyclerPedidos)
        floatingButton = v.findViewById(R.id.fab)
        return v
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        //aca el viewmodel se va a traer los pedidos de la db
        adapter = PedidoAdapter(pedidosViewModel.listaPedidos.value.orEmpty().toMutableList()) { position ->
            val pedidos = pedidosViewModel.listaPedidos.value.orEmpty()
            if (position in pedidos.indices) {
                val pedido = pedidos[position]
                pedidosViewModel.seleccionarPedido(pedido)
                val action = PedidosListDirections.actionPedidosListToInfoPedido(pedido)
                findNavController().navigate(action)
            }
        }

        recyclerPedidos.layoutManager = LinearLayoutManager(context)
        recyclerPedidos.adapter = adapter

        floatingButton.setOnClickListener {
            var action = PedidosListDirections.actionPedidosListToGenerarPedido()
            findNavController().navigate(action)
        }

        pedidosViewModel.listaPedidos.observe(viewLifecycleOwner) { pedidos ->
            adapter.actualizarPedidos(pedidos)
        }
    }
}






