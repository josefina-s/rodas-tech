package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class AddStockDialog : DialogFragment(){
    lateinit var cantStock : EditText
    lateinit var precioPorMetro : TextView
    lateinit var btnConfirm : Button
    lateinit var btnCancel : Button
    private val viewModel: ListClothViewModel by activityViewModels()
    private val editViewModel: EditClothViewModel by activityViewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    lateinit var errorActualStock : TextView
    lateinit var cloth: Cloth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_agrega_stock, container, false)
        cantStock=view.findViewById(R.id.txtEditStock)
        precioPorMetro=view.findViewById(R.id.txtPriceMeter)
        btnConfirm=view.findViewById(R.id.btnConfirmStock)
        btnCancel=view.findViewById(R.id.btnCancelStock)
        errorActualStock=view.findViewById(R.id.txtErrorStockActual)
        cloth= viewModel.selectedCloth.value!!
        cantStock.setText(viewModel.selectedCloth.value!!.stockActual.toString())
        precioPorMetro.text = viewModel.selectedCloth.value!!.price.toString()

        return view
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        btnConfirm.setOnClickListener {
            clearErrorMsg(cantStock.text.toString())
            validateViewModel.validarFormulario(cantStock.text.toString())
            validateViewModel.formularioValido.observe(viewLifecycleOwner){
                if (it){
                    editViewModel.viewModelScope.launch {
                        cloth.stockActual=Integer.parseInt(cantStock.text.toString())
                        editViewModel.updateClothStock(cloth)
                        Toast.makeText(requireContext(), "Se actualizó el stock para el producto: ${cloth.name}", Toast.LENGTH_SHORT).show()
                        viewModel.llamarGetAllCloths()
                        dismiss()
                    }
                }else {
                    setErrorMsg(cantStock.text.toString())
                    Toast.makeText(requireContext(), "ERROR: No se pude actualizar el producto}", Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }


    fun setErrorMsg(actualStock: String) {
        if (!validateViewModel.isValidActualStock(actualStock)) {
            errorActualStock.text = validateViewModel.errorActualStock.value.toString()
        }

    }
    fun clearErrorMsg(actualStock: String) {
        if (validateViewModel.isValidActualStock(actualStock)) {
            errorActualStock.text = ""
        }

    }


}