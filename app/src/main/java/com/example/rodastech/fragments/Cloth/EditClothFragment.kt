package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentDetailClothBinding
import com.example.rodastech.databinding.FragmentEditClothBinding
import com.example.rodastech.entities.Cloth
//import com.example.rodastech.fragments.EditClothFragmentArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditClothFragment : Fragment() {
    private val viewModel: EditClothViewModel by viewModels()
    val createViewModel:  CreateClothViewModel by activityViewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private lateinit var binding: FragmentEditClothBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentEditClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val args= EditClothFragmentArgs.fromBundle(requireArguments()).cloth
        validateViewModel.cloth.value=args
        binding.txtEditClothName.setText(args.name)
        binding.txtEditClothDesc.setText(args.description)
        binding.txtEditClothColor.setText(args.color)
        binding.txtEditClothWidth.setText(args.width.toString())
        binding.txtEditClothLong.setText(args.long.toString())
        binding.txtEditClothProvider.setText(args.provider)
        binding.txtEditClothStock.setText(args.stockActual.toString())



        binding.btnSave.setOnClickListener{
            args.name=binding.txtEditClothName.text.toString()
            args.description=binding.txtEditClothDesc.text.toString()
            args.color=binding.txtEditClothColor.text.toString()
            args.width=Integer.parseInt(binding.txtEditClothWidth.text.toString())
            args.long=Integer.parseInt(binding.txtEditClothLong.text.toString())
            args.provider=binding.txtEditClothProvider.text.toString()
            args.stockActual=Integer.parseInt(binding.txtEditClothStock.text.toString())
            clearErrorMsg(
                args.name.toString(),
                args.description.toString(),
                args.color.toString(),
                args.provider.toString(),
                args.width.toString(),
                args.long.toString(),
                args.stockMinimo.toString()
            )
            validateViewModel.validarFormulario(
                args.name.toString(),
                args.description.toString(),
                args.color.toString(),
                args.provider.toString(),
                args.width.toString(),
                args.long.toString(),
                args.stockMinimo.toString()
            )

            validateViewModel.formularioValido.observe(viewLifecycleOwner){
                if(it){
                    viewModel.viewModelScope.launch {
                        viewModel.updateCloth(args)
                        Log.d("MHTEST", "ESTOY EN EL TRUE del formulario valido en fragment")
                        val snackBar=Snackbar.make(binding.root,"Se modificó correctamente el producto", Snackbar.LENGTH_SHORT)
                        snackBar.view.setBackgroundColor(Color.parseColor("#A9EF90"))
                        snackBar.show()
                    }
                }
                else{
                    setErrorMsg(
                        args.name.toString(),
                        args.description.toString(),
                        args.color.toString(),
                        args.provider.toString(),
                        args.width.toString(),
                        args.long.toString(),
                        args.stockMinimo.toString()
                    )
                    Log.d("MHTEST", "ESTOY EN EL false del formulario valido en fragment")
                    val snackBar=Snackbar.make(binding.root,"ERROR:No se pudo modificar el producto, revise los campos con errores", Snackbar.LENGTH_SHORT)
                    snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                    snackBar.show()
                }
            }
        }


    }

    fun setErrorMsg(name: String, desc: String, color: String,provider: String,  width: String, long: String, initialStock: String) {
        if (!validateViewModel.isValidName(name)) {
            binding.txtErrorNombre.text = validateViewModel.errorNombre.value.toString()
        }
        if (!validateViewModel.isValidDesc(desc)) {
            binding.txtErrorClothDesc.text = validateViewModel.errorDesc.value.toString()
        }
        if (!validateViewModel.isValidColor(color)) {
            binding.txtErrorClothColor.text = validateViewModel.errorColor.value.toString()
        }
        if (!validateViewModel.isValidProvider(provider)) {
            binding.txtErrorClothProvider.text = validateViewModel.errorProv.value.toString()
        }
        if (!validateViewModel.isValidWidth(width)) {
            binding.txtErrorClothWidth.text = validateViewModel.errorWidth.value.toString()
        }
        if (!validateViewModel.isValidLong(long)) {
            binding.txtErrorClothInitialLong.text = validateViewModel.errorLong.value.toString()
        }
        if (!validateViewModel.isValidInitialStock(initialStock)) {
            binding.txtErrorClothInitialStock.text = validateViewModel.errorInitialStock.value.toString()
        }
    }
    fun clearErrorMsg(name: String, desc: String,  color: String, provider: String, width: String, long: String, initialStock: String) {
        if (validateViewModel.isValidName(name)) {
            binding.txtErrorNombre.text = ""
        }
        if (validateViewModel.isValidDesc(desc)) {
            binding.txtErrorClothDesc.text = ""
        }
        if (validateViewModel.isValidColor(color)) {
            binding.txtErrorClothColor.text = ""
        }
        if (validateViewModel.isValidProvider(provider)) {
            binding.txtErrorClothProvider.text = ""
        }
        if (validateViewModel.isValidWidth(width)) {
            binding.txtErrorClothWidth.text = ""
        }
        if (validateViewModel.isValidLong(long)) {
            binding.txtErrorClothInitialLong.text = ""
        }
        if (validateViewModel.isValidInitialStock(initialStock)) {
            binding.txtErrorClothInitialStock.text = ""
        }
    }


}