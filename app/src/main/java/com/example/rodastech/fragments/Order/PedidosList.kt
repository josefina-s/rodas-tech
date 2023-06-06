package com.example.rodastech.fragments.Order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.PedidoAdapter
import com.example.rodastech.databinding.FragmentGenerarPedidoBinding
import com.example.rodastech.databinding.FragmentListClothBinding
import com.example.rodastech.databinding.FragmentPedidosListBinding
import com.example.rodastech.entities.Pedido
import com.example.rodastech.entities.ProductoPedido
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class PedidosList : Fragment() {

    private val pedidosViewModel: PedidosListViewModel by activityViewModels()
    private lateinit var binding: FragmentPedidosListBinding
    lateinit var adapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPedidosListBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.RecyclerPedidos.layoutManager=LinearLayoutManager(context)
        pedidosViewModel.getAllPedidos()
        pedidosViewModel.listaPedidos.observe(viewLifecycleOwner){pedidos->
            adapter= PedidoAdapter(pedidos){ position->
                pedidosViewModel.seleccionarPedido(pedidos[position])
                val action = PedidosListDirections.actionPedidosListToInfoPedido(pedidos[position])
                findNavController().navigate(action)
            }
            binding.RecyclerPedidos.adapter=adapter
        }
        pedidosViewModel.listaPedidos.observe(viewLifecycleOwner) { pedidos ->
            adapter.actualizarPedidos(pedidos)
        }
        binding.fab.setOnClickListener {
            var action = PedidosListDirections.actionPedidosListToGenerarPedido()
            findNavController().navigate(action)
        }

    }
}






