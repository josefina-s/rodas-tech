package com.example.rodastech.fragments.Order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth
import com.example.rodastech.entities.ProductoPedido
import com.example.rodastech.fragments.Cloth.ListClothViewModel
import com.google.android.material.textfield.TextInputLayout

class AgregarProductoDialog : DialogFragment() {
    lateinit var menuSeleccionarProducto : TextInputLayout
    lateinit var dropDownProductos : AutoCompleteTextView
    lateinit var numPickerCantidad : NumberPicker
    lateinit var btnConfirmarProducto : Button
    lateinit var errorStock : TextView
    val pedidoViewModel : GenerarPedidoViewModel by activityViewModels()
    var nuevoProducto : ProductoPedido = ProductoPedido("",0)
    private val clothViewModel : ListClothViewModel by activityViewModels()
    private lateinit var idCloth: String


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
        numPickerCantidad.setFormatter(object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                return String.format("%d", value)
            }
        })
        errorStock=view.findViewById(R.id.txtErrorCantStock)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clothViewModel.llamarGetAllCloths()
        clothViewModel.cloths.observe(viewLifecycleOwner){
            val adapter= ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,it)
            dropDownProductos.setAdapter(adapter)
        }
        dropDownProductos.setOnItemClickListener { parent, view, position, id ->
            errorStock.text=""
            val productoSeleccionado = parent.getItemAtPosition(position) as Cloth
            idCloth= productoSeleccionado.id.toString()
            nuevoProducto.nombre = productoSeleccionado.name
        }

        btnConfirmarProducto.setOnClickListener {
            agregarMetros()
            if (isValidStock(idCloth)){
                pedidoViewModel.agregarProducto(nuevoProducto)
                val msj = "Dialog: Se agregó un producto: ${nuevoProducto.nombre} ${nuevoProducto.metros} metros"
                Log.d("josie_test", msj)
                dropDownProductos.setText("", false)
                dismiss()
            }

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
        nuevoProducto.metros = cantidadSeleccionada
    }

    @SuppressLint("SetTextI18n")
    fun isValidStock(id :String):Boolean{

        for (c in clothViewModel.cloths.value!!)
            if (c.id==id){
                if (c.stockActual!! <nuevoProducto.metros){
                    errorStock.text="No hay stock disponible para los ${nuevoProducto.metros.toString()} mts solicitados, el stock actual es ${c.stockActual.toString()} mts"
                    return false
                }
            }
        return true
    }

}


