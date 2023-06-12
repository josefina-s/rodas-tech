package com.example.rodastech.fragments.Order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ProductoPedidoAdapter
import com.example.rodastech.databinding.FragmentGenerarPedidoBinding
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Pedido
import com.example.rodastech.entities.ProductoPedido
import com.example.rodastech.fragments.Client.ClientViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GenerarPedido : Fragment() {
    private lateinit var adapterProductosPedido: ProductoPedidoAdapter
    private val generarPedidoViewModel: GenerarPedidoViewModel by activityViewModels()
    private val clientesViewModel: ClientViewModel by activityViewModels()
    private lateinit var binding: FragmentGenerarPedidoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapterProductosPedido = ProductoPedidoAdapter(mutableListOf())
        binding = FragmentGenerarPedidoBinding.inflate(inflater, container, false)
        binding.productosPedido.adapter = adapterProductosPedido
        binding.productosPedido.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        val navController = findNavController()
        var clientName: String?=""

        clientesViewModel.getClientsList()
        clientesViewModel.dbClients.observe(viewLifecycleOwner) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it)
            binding.dropdownClientes.setAdapter(adapter)
        }


        binding.dropdownClientes.setOnItemClickListener { parent, v, position, id ->
            val clienteSeleccionado = parent.getItemAtPosition(position) as Client
            generarPedidoViewModel.setClienteSeleccionado(clienteSeleccionado)
            clientName=clienteSeleccionado.name
        }

        binding.btnAgregarProducto.setOnClickListener {
            val dialogFragment = AgregarProductoDialog()
            dialogFragment.show(parentFragmentManager, "AgregarProductoDialog")
        }
        generarPedidoViewModel.nuevoProducto.observe(viewLifecycleOwner) { producto ->
            generarPedidoViewModel.agregarProductoALista(producto)
        }

        generarPedidoViewModel.listaProductos.observe(viewLifecycleOwner) { productos ->
            adapterProductosPedido.actualizarProductos(productos)
        }

        binding.btnConfirmarPedido.setOnClickListener {
            val myUuid = UUID.randomUUID()
            val myUuidAsString = myUuid.toString()
            clearErrorMsg()
            val clienteSeleccionado = Pedido(
                generarPedidoViewModel.clienteSeleccionado.value?.name,
                obtenerFechaActual(),
                myUuidAsString
            )
            val listaProductos =
                generarPedidoViewModel.listaProductos.value.orEmpty().toMutableList()

            if (isValidOrder(clientName, listaProductos)) {
                generarPedidoViewModel.viewModelScope.launch {
                    generarPedidoViewModel.insertPedido(clienteSeleccionado)
                    generarPedidoViewModel.insertProductosPedidos(listaProductos, myUuidAsString)
                    val snackBar = Snackbar.make(
                        binding.root,
                        "Se generó correctamente el pedido",
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.view.setBackgroundColor(Color.parseColor("#33363F"))
                    snackBar.show()
                    navController.popBackStack()
                }
            } else {
                val snackBar = Snackbar.make(
                    binding.root,
                    "ERROR:No se pudo crear el pedido, revise los campos con errores",
                    Snackbar.LENGTH_SHORT
                )
                snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                snackBar.show()
            }
        }

        binding.btnClearPedido.setOnClickListener {
            limpiarFormulario()

        }

    }

    private fun obtenerFechaActual(): String {
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.now().format(formato)
    }

    private fun limpiarFormulario() {
        binding.dropdownClientes.text = null
        generarPedidoViewModel.limpiarPedido()
    }

    override fun onResume() {
        super.onResume()
        adapterProductosPedido.clearProductos()
    }
    private fun clearErrorMsg(){
        binding.txtErrorProductsOrder.text = ""
        binding.txtErrorClientOrder.text = ""
    }

    private fun isValidOrder(client: String?, productos: MutableList<ProductoPedido>): Boolean {
        var isValid: Boolean = false
        if (client.isNullOrEmpty()) {
            binding.txtErrorClientOrder.text = "Debe seleccionar un cliente"
        }
        if (productos.isNullOrEmpty() || productos.size == 0) {
            binding.txtErrorProductsOrder.text= "Debe seleccionar al menos un producto"
        } else {
            isValid = true
        }
        return isValid
    }

}



