package com.example.rodastech.fragments.Order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ProductoPedidoAdapter
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Pedido
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GenerarPedido : Fragment() {
    private lateinit var menuSeleccionarCliente: TextInputLayout
    private lateinit var dropDownClientes: AutoCompleteTextView
    private lateinit var btnAddProducto: Button
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapterProductosPedido: ProductoPedidoAdapter
    private  val pedViewModel: GenerarPedidoViewModel by activityViewModels()
    private  val pedidosViewModel: PedidosListViewModel by activityViewModels()
    private lateinit var btnConfirmar : Button
    private lateinit var btnCancelar : Button



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_generar_pedido, container, false)
        btnAddProducto = v.findViewById(R.id.btnAgregarProducto)
        btnConfirmar = v.findViewById(R.id.btnConfirmarPedido)
        btnCancelar = v.findViewById(R.id.btnClearPedido)

        dropDownClientes = v.findViewById(R.id.dropdownClientes)
        menuSeleccionarCliente = v.findViewById(R.id.seleccionarCliente)
        recyclerProductos = v.findViewById(R.id.productosPedido)

        adapterProductosPedido = ProductoPedidoAdapter(mutableListOf())
        recyclerProductos.adapter = adapterProductosPedido
        recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        return v
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        val navController = findNavController()

        val clientes = arrayOf(
            Client("Empresa Telas", "Empresa Telas", "816544123"),
            Client("Jeans cools", "Jeans cools", "806789456"),
            Client("jyjeans", "AvellanedaTex", "876544789")
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, clientes)
        dropDownClientes.setAdapter(adapter)

        dropDownClientes.setOnItemClickListener { parent, v, position, id ->
            val clienteSeleccionado = parent.getItemAtPosition(position) as Client
            pedViewModel.setClienteSeleccionado(clienteSeleccionado)
        }

        btnAddProducto.setOnClickListener {
            val dialogFragment = AgregarProductoDialog()
            dialogFragment.show(parentFragmentManager, "AgregarProductoDialog")
        }

        btnConfirmar.setOnClickListener {
            val clienteSeleccionado = pedViewModel.clienteSeleccionado.value
            val listaProductos = pedViewModel.listaProductos.value.orEmpty().toMutableList()
            if (clienteSeleccionado != null) {
                val nuevoPedido = clienteSeleccionado.name?.let { it1 ->
                    Pedido(
                        obtenerFechaActual(), it1, listaProductos
                    )
                }
                //aca se le pega a la db
                if (nuevoPedido != null) {
                    pedidosViewModel.agregarPedido(nuevoPedido)
                }
                limpiarFormulario()
                navController.popBackStack()
            }
        }


        btnCancelar.setOnClickListener {
            limpiarFormulario()

        }

        pedViewModel.nuevoProducto.observe(viewLifecycleOwner) { producto ->
            pedViewModel.agregarProductoALista(producto)
        }

        pedViewModel.listaProductos.observe(viewLifecycleOwner) { productos ->
            adapterProductosPedido.actualizarProductos(productos)
        }


    }

    private fun obtenerFechaActual(): String {
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.now().format(formato)
    }

    private fun limpiarFormulario() {
        dropDownClientes.text = null
        pedViewModel.limpiarPedido()
    }

    override fun onResume() {
        super.onResume()
        adapterProductosPedido.clearProductos()
    }

}



