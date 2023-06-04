package com.example.rodastech.fragments.Order


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ProductoPedidoAdapter
import com.example.rodastech.databinding.FragmentInfoPedidoBinding
import com.example.rodastech.entities.Cloth
import com.example.rodastech.entities.Pedido
import com.example.rodastech.entities.ProductoPedido
import kotlinx.coroutines.launch

class InfoPedido : Fragment() {


    private  val pedidosViewModel: PedidosListViewModel by activityViewModels()
    lateinit var adapter: ProductoPedidoAdapter
    private lateinit var binding : FragmentInfoPedidoBinding
    lateinit var pedido : Pedido


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentInfoPedidoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.productos.layoutManager=LinearLayoutManager(context)
        try {
            pedido=pedidosViewModel.pedidoSeleccionado.value!!
            binding.txtClienteTitle.text=pedido.cliente
            binding.txtFechaSubT.text=pedido.fecha
            pedidosViewModel.getProductosPedidos(pedido)
        }catch (e: Exception){
            Log.d("MHTEST", e.toString())
        }

        pedidosViewModel.productosPedidosSeleccionados.observe(viewLifecycleOwner){
            adapter= ProductoPedidoAdapter(it)
            binding.productos.adapter=adapter
        }


    }
}
