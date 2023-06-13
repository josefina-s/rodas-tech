package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.databinding.FragmentCreateClothBinding
import com.example.rodastech.entities.Cloth
import com.example.rodastech.fragments.ValidateFormViewModel
//import com.example.rodastech.fragments.CreateClothFragmentArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.*

class CreateClothFragment : Fragment() {
    private val viewModel: CreateClothViewModel by viewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private val listViewModel: ListClothViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateClothBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateClothBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateClothSave.setOnClickListener {


            clearErrorMsg(
                binding.txtCreateClothName.text.toString(),
                binding.txtCreateClothDesc.text.toString(),
                binding.txtCreateClothColor.text.toString(),
                binding.txtCreateClothProvider.text.toString(),
                binding.txtCreateClothWidth.text.toString(),
                binding.txtCreateClothLong.text.toString(),
                binding.txtCreateClothInitialStock.text.toString(),
                binding.txtCreateClothPrice.text.toString()
            )
            clearExistsErrorMsg()

            validateViewModel.validarFormulario(
                binding.txtCreateClothName.text.toString(),
                binding.txtCreateClothDesc.text.toString(),
                binding.txtCreateClothColor.text.toString(),
                binding.txtCreateClothProvider.text.toString(),
                binding.txtCreateClothWidth.text.toString(),
                binding.txtCreateClothLong.text.toString(),
                binding.txtCreateClothInitialStock.text.toString(),
                binding.txtCreateClothPrice.text.toString()
            )
            val existCloth=clothExists(binding.txtCreateClothName.text.toString(),binding.txtCreateClothProvider.text.toString())
            if (existCloth){
                setExistsErrorMsg()
            }else{
                clearExistsErrorMsg()
            }


            validateViewModel.formularioValido.observe(viewLifecycleOwner) {
                if (it) {
                    if (!existCloth) {
                        viewModel.viewModelScope.launch {
                            val myUuid = UUID.randomUUID()
                            val myUuidAsString = myUuid.toString()
                            val cloth = Cloth(
                                myUuidAsString,
                                binding.txtCreateClothName.text.toString().capitalize(),
                                binding.txtCreateClothDesc.text.toString().capitalize(),
                                binding.txtCreateClothColor.text.toString().capitalize(),
                                binding.txtCreateClothProvider.text.toString().capitalize(),
                                Integer.parseInt(binding.txtCreateClothWidth.text.toString()),
                                Integer.parseInt(binding.txtCreateClothLong.text.toString()),
                                Integer.parseInt(binding.txtCreateClothPrice.text.toString()),
                                0,
                                Integer.parseInt(binding.txtCreateClothInitialStock.text.toString())
                            )
                            viewModel.insertCloth(cloth)
                            Log.d("RODASTECH", "ESTOY EN EL TRUE del formulario valido en fragment")
                            val snackBar = Snackbar.make(
                                binding.root,
                                "Se agregó correctamente el producto",
                                Snackbar.LENGTH_SHORT
                            )
                            snackBar.view.setBackgroundColor(Color.parseColor("#33363F"))
                            snackBar.show()
                            val navController = findNavController()
                            navController.popBackStack()
                        }
                    } else {
                        setExistsErrorMsg()
                    }
                } else {
                    setErrorMsg(
                        binding.txtCreateClothName.text.toString(),
                        binding.txtCreateClothDesc.text.toString(),
                        binding.txtCreateClothColor.text.toString(),
                        binding.txtCreateClothProvider.text.toString(),
                        binding.txtCreateClothWidth.text.toString(),
                        binding.txtCreateClothLong.text.toString(),
                        binding.txtCreateClothInitialStock.text.toString(),
                        binding.txtCreateClothPrice.text.toString()
                    )
                    Log.d("RODASTECH", "ESTOY EN EL false del formulario valido en fragment")
                    val snackBar = Snackbar.make(
                        binding.root,
                        "ERROR:No se pudo guardar el producto, revise los campos con errores",
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                    snackBar.show()
                }
            }
        }

    }

    fun setErrorMsg(
        name: String,
        desc: String,
        color: String,
        provider: String,
        width: String,
        long: String,
        initialStock: String,
        price: String
    ) {
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
            binding.txtErrorClothInitialStock.text =
                validateViewModel.errorInitialStock.value.toString()
        }
        if (!validateViewModel.isValidPrice(price)) {
            binding.txtErrorClothPrice.text = validateViewModel.errorPrice.value.toString()
        }
    }


    fun clearErrorMsg(
        name: String,
        desc: String,
        color: String,
        provider: String,
        width: String,
        long: String,
        initialStock: String,
        price: String
    ) {
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

    fun setExistsErrorMsg(
    ) {
        binding.txtErrorNombre.text = "Ya existe una tela con ese nombre registrado"
        binding.txtErrorClothProvider.text ="Ya existe una tela con ese proveedor registrado"
    }

    fun clearExistsErrorMsg(
    ) {
        binding.txtErrorNombre.text = ""
        binding.txtErrorClothProvider.text = ""
    }

    fun clothExists(name: String, provider: String): Boolean {
        var filterList: MutableList<Cloth>
        filterList = listViewModel.cloths.value!!.filter { cloth ->
            cloth.name!!.trim().lowercase()==name.trim().lowercase() && cloth.provider!!.trim().lowercase() == provider.trim().lowercase()
        } as MutableList<Cloth>
        if (filterList.isNullOrEmpty()) {
            return false
        } else {
            return true
        }
    }
}