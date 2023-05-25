package com.example.rodastech.fragments.Order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.rodastech.R
import com.example.rodastech.entities.ProductoPedido
import com.google.android.material.textfield.TextInputLayout

class AgregarProductoDialog : DialogFragment() {
    lateinit var menuSeleccionarProducto : TextInputLayout
    lateinit var dropDownProductos : AutoCompleteTextView
    lateinit var numPickerCantidad : NumberPicker
    lateinit var btnConfirmarProducto : Button
    val pedidoViewModel : GenerarPedidoViewModel by activityViewModels()
    var nuevoProducto : ProductoPedido = ProductoPedido("","")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_agregar_producto, container, false)

        menuSeleccionarProducto = view.findViewById(R.id.seleccionarProducto)
        dropDownProductos = view.findViewById(R.id.dropdownProductos)
        btnConfirmarProducto = view.findViewById(R.id.btnConfirmar)

        numPickerCantidad = view.findViewById(R.id.numberPickerCantidad);

        numPickerCantidad.minValue = 1;
        numPickerCantidad.maxValue = 12222222;
        numPickerCantidad.value = 1;
        numPickerCantidad.wrapSelectorWheel = false

        dropDownProductos.setOnItemClickListener { parent, view, position, id ->
            val productoSeleccionado = parent.getItemAtPosition(position) as ProductoPedido
            nuevoProducto.nombre = productoSeleccionado.nombre
        }

        numPickerCantidad.setFormatter(object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                return String.format("%d", value)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productos = arrayOf(
            ProductoPedido("Jean Azul", "3000"),
            ProductoPedido("Jean Negro", "4000"),
            ProductoPedido("Jean Blanco", "4000"),
            ProductoPedido("Jean Mezclilla", "4000")
        )

        var adapterProductos = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line , productos)
        dropDownProductos.setAdapter(adapterProductos)


        btnConfirmarProducto.setOnClickListener {
            agregarMetros()
            pedidoViewModel.agregarProducto(nuevoProducto)
            val msj = "Dialog: Se agregó un producto: ${nuevoProducto.nombre} ${nuevoProducto.metros} metros"
            Log.d("josie_test", msj)
            dropDownProductos.setText("", false)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    fun agregarMetros() {
        val cantidadSeleccionada = numPickerCantidad.getValue()
        nuevoProducto.metros = cantidadSeleccionada.toString()
    }

}


