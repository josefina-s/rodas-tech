package com.example.rodastech.fragments.Order


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ProductoPedidoAdapter

class InfoPedido : Fragment() {

    private lateinit var clienteTextView: TextView
    private lateinit var fechaTextView: TextView
    private lateinit var productosTxt: TextView
    private lateinit var recyclerProductos: RecyclerView
    private  val pedidosViewModel: PedidosListViewModel by activityViewModels()
    lateinit var adapter: ProductoPedidoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info_pedido, container, false)
        clienteTextView = view.findViewById(R.id.txtClienteTitle)
        fechaTextView = view.findViewById(R.id.txtFechaSubT)
        productosTxt = view.findViewById(R.id.txtProductosList)
        recyclerProductos = view.findViewById(R.id.productos)

        return view
    }


    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        pedidosViewModel.pedidoSeleccionado.observe(viewLifecycleOwner) { pedido ->
            clienteTextView.text = pedido?.cliente
            fechaTextView.text = pedido?.fecha
            adapter.actualizarProductos(pedido?.productos.orEmpty().toMutableList())

        }
        adapter = ProductoPedidoAdapter(mutableListOf())
        recyclerProductos.layoutManager = LinearLayoutManager(context)
        recyclerProductos.adapter = adapter
    }
}
