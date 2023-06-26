package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.databinding.FragmentEditClothBinding
import com.example.rodastech.entities.Cloth
import com.example.rodastech.fragments.ValidateFormViewModel
//import com.example.rodastech.fragments.EditClothFragmentArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditClothFragment : Fragment() {
    private val viewModel: EditClothViewModel by activityViewModels()
    private val listViewModel: ListClothViewModel by activityViewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private lateinit var binding: FragmentEditClothBinding
    lateinit var cloth: Cloth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEditClothBinding.inflate(inflater,container,false)
        cloth= listViewModel.selectedCloth.value!!
        binding.txtEditClothName.setText(cloth.name)
        binding.txtEditClothDesc.setText(cloth.description)
        binding.txtEditClothColor.setText(cloth.color)
        binding.txtEditClothWidth.setText(cloth.width.toString())
        binding.txtEditClothLong.setText(cloth.long.toString())
        binding.txtEditClothProvider.setText(cloth.provider)
        binding.txtEditClothStock.setText(cloth.stockMinimo.toString())
        binding.txtEditClothPrice.setText(cloth.price.toString())
        binding.txtEditClothName.isEnabled=false
        binding.txtEditClothProvider.isEnabled=false
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnSave.setOnClickListener{
            clearErrorMsg(
                binding.txtEditClothName.text.toString(),
                binding.txtEditClothDesc.text.toString(),
                binding.txtEditClothColor.text.toString(),
                binding.txtEditClothProvider.text.toString(),
                binding.txtEditClothWidth.text.toString(),
                binding.txtEditClothLong.text.toString(),
                binding.txtEditClothStock.text.toString(),
                binding.txtEditClothPrice.text.toString()
            )
            validateViewModel.validarFormulario(
                binding.txtEditClothName.text.toString(),
                binding.txtEditClothDesc.text.toString(),
                binding.txtEditClothColor.text.toString(),
                binding.txtEditClothProvider.text.toString(),
                binding.txtEditClothWidth.text.toString(),
                binding.txtEditClothLong.text.toString(),
                binding.txtEditClothStock.text.toString(),
                binding.txtEditClothPrice.text.toString()
            )
            validateViewModel.formularioValido.observe(viewLifecycleOwner){
                if(it){
                    viewModel.viewModelScope.launch {
                        cloth.name=binding.txtEditClothName.text.toString()
                        cloth.description=binding.txtEditClothDesc.text.toString()
                        cloth.color=binding.txtEditClothColor.text.toString()
                        cloth.width=Integer.parseInt(binding.txtEditClothWidth.text.toString())
                        cloth.long=Integer.parseInt(binding.txtEditClothLong.text.toString())
                        cloth.provider=binding.txtEditClothProvider.text.toString()
                        cloth.stockMinimo=Integer.parseInt(binding.txtEditClothStock.text.toString())
                        cloth.price=Integer.parseInt(binding.txtEditClothPrice.text.toString())
                        viewModel.updateCloth(cloth)
                        Log.d("RODASTECH", "ESTOY EN EL TRUE del formulario valido en fragment")
                        val snackBar=Snackbar.make(binding.root,"Se modificó correctamente el producto ${cloth.name}", Snackbar.LENGTH_SHORT)
                        snackBar.view.setBackgroundColor(Color.parseColor("#33363F"))
                        snackBar.show()
                        val navController = findNavController()
                        navController.popBackStack()
                    }
                }
                else{
                    setErrorMsg(
                        binding.txtEditClothName.text.toString(),
                        binding.txtEditClothDesc.text.toString(),
                        binding.txtEditClothColor.text.toString(),
                        binding.txtEditClothProvider.text.toString(),
                        binding.txtEditClothWidth.text.toString(),
                        binding.txtEditClothLong.text.toString(),
                        binding.txtEditClothStock.text.toString(),
                        binding.txtEditClothPrice.text.toString()
                    )
                    Log.d("RODASTECH", "ESTOY EN EL false del formulario valido en fragment")
                    val snackBar=Snackbar.make(binding.root,"ERROR:No se pudo modificar el producto, revise los campos con errores", Snackbar.LENGTH_SHORT)
                    snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                    snackBar.show()
                }
            }
        }


    }

    fun setErrorMsg(name: String, desc: String, color: String,provider: String,  width: String, long: String, initialStock: String, price: String) {
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
        if (!validateViewModel.isValidPrice(price)) {
            binding.txtErrorClothPrice.text = validateViewModel.errorPrice.value.toString()
        }
    }
    fun clearErrorMsg(name: String, desc: String,  color: String, provider: String, width: String, long: String, initialStock: String, price : String) {
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
        if (validateViewModel.isValidPrice(price)) {
            binding.txtErrorClothPrice.text = ""
        }
    }


}