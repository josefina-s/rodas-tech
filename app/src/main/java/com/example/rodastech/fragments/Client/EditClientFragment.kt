package com.example.rodastech.fragments.Client

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentEditClientBinding
import com.example.rodastech.databinding.FragmentEditClothBinding
import com.example.rodastech.fragments.Cloth.EditClothFragmentArgs
import com.example.rodastech.fragments.Cloth.EditClothViewModel
import com.example.rodastech.fragments.Cloth.ValidateFormViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditClientFragment : Fragment() {
    private val viewModel: EditClientViewModel by viewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private lateinit var binding: FragmentEditClientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEditClientBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        val args= EditClientFragmentArgs.fromBundle(requireArguments()).client
        binding.txtEditClientName.setText(args.name)
        binding.txtEditClientCuit.setText(args.cuit)
        binding.txtEditClientContactPerson.setText(args.contactPerson)
        binding.txtEditClientPhone.setText(args.telephone.toString())
        binding.txtEditClientEmail.setText(args.email)
        binding.txtEditClientAddress.setText(args.address)
        binding.txtEditClientPostalAddress.setText(args.cp.toString())


        binding.btnSaveEditClient.setOnClickListener(){
            clearErrorMsg(
                args.name.toString(),
                args.contactPerson.toString(),
                args.telephone.toString(),
                args.email.toString(),
                args.address.toString(),
                args.cp.toString()
            )
            validateViewModel.validarFormulario(
                args.name.toString(),
                args.contactPerson.toString(),
                args.telephone.toString(),
                args.email.toString(),
                args.address.toString(),
                args.cp.toString()
            )
            validateViewModel.formularioValido.observe(viewLifecycleOwner){
                if (it){
                    viewModel.viewModelScope.launch {
                        args.name=binding.txtEditClientName.text.toString()
                        args.cuit=binding.txtEditClientCuit.text.toString()
                        args.contactPerson=binding.txtEditClientContactPerson.text.toString()
                        args.telephone=Integer.parseInt(binding.txtEditClientPhone.text.toString())
                        args.email=binding.txtEditClientEmail.text.toString()
                        args.address=binding.txtEditClientAddress.text.toString()
                        args.cp=Integer.parseInt(binding.txtEditClientPostalAddress.text.toString())
                        viewModel.updateClient(args)
                        Log.d("MHTEST", "ESTOY EN EL TRUE del formulario valido en fragment Edit Client")
                        val snackBar= Snackbar.make(binding.root,"Se modificó correctamente el cliente", Snackbar.LENGTH_SHORT)
                        snackBar.view.setBackgroundColor(Color.parseColor("#A9EF90"))
                        snackBar.show()
                    }
                }else{
                    setErrorMsg(
                        args.name.toString(),
                        args.contactPerson.toString(),
                        args.telephone.toString(),
                        args.email.toString(),
                        args.address.toString(),
                        args.cp.toString()
                    )
                    Log.d("MHTEST", "ESTOY EN EL false del formulario valido en fragment Edit Client")
                    val snackBar=Snackbar.make(binding.root,"ERROR:No se pudo modificar el cliente, revise los campos con errores", Snackbar.LENGTH_SHORT)
                    snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                    snackBar.show()
                }
            }
        }
    }


    fun setErrorMsg(name: String, contactPerson: String, phoneNumber: String,email: String,address: String,  postalAdress: String) {
        if (!validateViewModel.isValidName(name)) {
            binding.txtErrorClientName.text = validateViewModel.errorNombre.value.toString()
        }
        if (!validateViewModel.isValidContactPerson(contactPerson)) {
            binding.txtErrorClientContactPerson.text = validateViewModel.errorContactPerson.value.toString()
        }
        if (!validateViewModel.isValidPhoneNumber(phoneNumber)) {
            binding.txtErrorClientPhone.text = validateViewModel.errorColor.value.toString()
        }
        if (!validateViewModel.isValidEmail(email)) {
            binding.txtErrorClientEmail.text = validateViewModel.errorEmail.value.toString()
        }
        if (!validateViewModel.isValidProvider(address)) {
            binding.txtErrorClientAddress.text = validateViewModel.errorAddress.value.toString()
        }
        if (!validateViewModel.isValidWidth(postalAdress)) {
            binding.txtErrorClientPostalAddress.text = validateViewModel.errorPostalAddress.value.toString()
        }
    }

    fun clearErrorMsg(name: String, contactPerson: String, phoneNumber: String,email: String,address: String,  postalAdress: String){
        if (validateViewModel.isValidName(name)) {
            binding.txtErrorClientName.text = ""
        }
        if (validateViewModel.isValidContactPerson(contactPerson)) {
            binding.txtErrorClientContactPerson.text = ""
        }
        if (validateViewModel.isValidPhoneNumber(phoneNumber)) {
            binding.txtErrorClientPhone.text = ""
        }
        if (validateViewModel.isValidEmail(email)) {
            binding.txtErrorClientEmail.text = ""
        }
        if (validateViewModel.isValidAddress(address)) {
            binding.txtErrorClientAddress.text = ""
        }
        if (validateViewModel.isValidPostalCode(postalAdress)) {
            binding.txtErrorClientPostalAddress.text = ""
        }
    }


}