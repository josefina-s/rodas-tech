package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.R
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.databinding.FragmentCreateClothBinding
import com.example.rodastech.databinding.FragmentEditClothBinding
import com.example.rodastech.entities.Cloth
//import com.example.rodastech.fragments.CreateClothFragmentArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*

class CreateClothFragment : Fragment() {
    private val viewModel: CreateClothViewModel by viewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateClothBinding
    private lateinit var v: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateClothBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateClothSave.setOnClickListener() {
            val myUuid = UUID.randomUUID()
            val myUuidAsString = myUuid.toString()

            clearErrorMsg(binding.txtCreateClothName.text.toString(),
                binding.txtCreateClothDesc.text.toString(),
                binding.txtCreateClothProvider.text.toString(),
                binding.txtCreateClothColor.text.toString(),
                binding.txtCreateClothWidth.text.toString(),
                binding.txtCreateClothLong.text.toString(),
                binding.txtCreateClothInitialStock.text.toString()
            )

            validateViewModel.validarFormulario(
                binding.txtCreateClothName.text.toString(),
                binding.txtCreateClothDesc.text.toString(),
                binding.txtCreateClothProvider.text.toString(),
                binding.txtCreateClothColor.text.toString(),
                binding.txtCreateClothWidth.text.toString(),
                binding.txtCreateClothLong.text.toString(),
                binding.txtCreateClothInitialStock.text.toString()
            )


            validateViewModel.formularioValido.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.viewModelScope.launch {
                    viewModel.insertCloth(Cloth(myUuidAsString,
                        binding.txtCreateClothName.text.toString(),
                        binding.txtCreateClothDesc.text.toString(),
                        binding.txtCreateClothProvider.text.toString(),
                        binding.txtCreateClothColor.text.toString(),
                        Integer.parseInt(binding.txtCreateClothWidth.text.toString()),
                        Integer.parseInt(binding.txtCreateClothLong.text.toString()),
                        0,
                        0,
                        Integer.parseInt(binding.txtCreateClothInitialStock.text.toString())))
                        Log.d("MHTEST", "ESTOY EN EL TRUE del formulario valido en fragment")
                        val snackBar = Snackbar.make(
                            binding.root,
                            "Se agregó correctamente el producto",
                            Snackbar.LENGTH_SHORT
                        )
                        snackBar.view.setBackgroundColor(Color.parseColor("#A9EF90"))
                        snackBar.show()
                    }

                } else {
                    setErrorMsg(binding.txtCreateClothName.text.toString(),
                        binding.txtCreateClothDesc.text.toString(),
                        binding.txtCreateClothColor.text.toString(),
                        binding.txtCreateClothProvider.text.toString(),
                        binding.txtCreateClothWidth.text.toString(),
                        binding.txtCreateClothLong.text.toString(),
                        binding.txtCreateClothInitialStock.text.toString())
                    Log.d("MHTEST", "ESTOY EN EL false del formulario valido en fragment")
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