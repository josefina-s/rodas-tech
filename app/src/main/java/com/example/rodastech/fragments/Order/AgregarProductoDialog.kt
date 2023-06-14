package com.example.rodastech.fragments.Order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private var idCloth: String=""


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
        numPickerCantidad.setOnValueChangedListener { _, _, newValue ->
            numPickerCantidad.value = newValue
        }
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
            nuevoProducto.idCloth=productoSeleccionado.id
        }

        btnConfirmarProducto.setOnClickListener {
            agregarMetros()
            if (isAddedProduct(nuevoProducto.nombre.toString())){
                if (isValidStock(idCloth)){
                    pedidoViewModel.agregarProducto(nuevoProducto)
                    val msj = "Se agregó el producto: ${nuevoProducto.nombre} con éxito"
                    Log.d("RODASTECH", msj)
                    dropDownProductos.setText("", false)
                    dismiss()
                    Toast.makeText(requireContext(), msj, Toast.LENGTH_SHORT).show()
                }
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
        if (id.isNullOrEmpty()){
            errorStock.text="Debe seleccionar una tela"
            return false
        }
        for (c in clothViewModel.cloths.value!!)
            if (c.id==id){
                if (c.stockActual!! <nuevoProducto.metros){
                    errorStock.text="No hay stock disponible para los ${nuevoProducto.metros} metros solicitados, el stock actual es ${c.stockActual.toString()} metros"
                    return false
                }
            }
        return true
    }
    fun isAddedProduct(name :String) :Boolean{
        var filterList: MutableList<ProductoPedido>

        if( pedidoViewModel.listaProductos.value.isNullOrEmpty()){
            return true
        }else{
            filterList= pedidoViewModel.listaProductos.value!!.filter {p->p.nombre!!.lowercase().contains(name.lowercase()) } as MutableList<ProductoPedido>
        }
        if (filterList.isNullOrEmpty()){
            return true
        }else{
            errorStock.text="No se puede ingresar más de una vez el producto, si quiere editar los metros solicitados, limpie la pantalla y vuelva a ingresar"
            return false
        }
    }

}


