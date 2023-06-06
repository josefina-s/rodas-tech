package com.example.rodastech.fragments.Client


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
import com.example.rodastech.databinding.FragmentEditClientBinding
import com.example.rodastech.entities.Client
import com.example.rodastech.fragments.Cloth.ValidateFormViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditClientFragment : Fragment() {
    private val viewModel: EditClientViewModel by viewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private lateinit var binding: FragmentEditClientBinding
    private val clientViewModel: ClientViewModel by activityViewModels()
    lateinit var client: Client

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        client=clientViewModel.selectedClient.value!!
        binding=FragmentEditClientBinding.inflate(inflater,container,false)
        binding.txtEditClientName.setText(client.name)
        binding.txtEditClientCuit.setText(client.cuit)
        binding.txtEditClientContactPerson.setText(client.contactPerson)
        binding.txtEditClientPhone.setText(client.telephone.toString())
        binding.txtEditClientEmail.setText(client.email)
        binding.txtEditClientAddress.setText(client.address)
        binding.txtEditClientPostalAddress.setText(client.cp.toString())
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.btnSaveEditClient.setOnClickListener{
            clearErrorMsg(
                binding.txtEditClientName.text.toString(),
                binding.txtEditClientCuit.text.toString(),
                binding.txtEditClientContactPerson.text.toString(),
                binding.txtEditClientPhone.text.toString(),
                binding.txtEditClientEmail.text.toString(),
                binding.txtEditClientAddress.text.toString(),
                binding.txtEditClientPostalAddress.text.toString()
            )
            validateViewModel.validarFormulario(
                binding.txtEditClientName.text.toString(),
                binding.txtEditClientCuit.text.toString(),
                binding.txtEditClientContactPerson.text.toString(),
                binding.txtEditClientPhone.text.toString(),
                binding.txtEditClientEmail.text.toString(),
                binding.txtEditClientAddress.text.toString(),
                binding.txtEditClientPostalAddress.text.toString()
            )
            validateViewModel.formularioValido.observe(viewLifecycleOwner){
                if (it){
                    viewModel.viewModelScope.launch {
                        client.name=binding.txtEditClientName.text.toString()
                        client.cuit=binding.txtEditClientCuit.text.toString()
                        client.contactPerson=binding.txtEditClientContactPerson.text.toString()
                        client.telephone=Integer.parseInt(binding.txtEditClientPhone.text.toString())
                        client.email=binding.txtEditClientEmail.text.toString()
                        client.address=binding.txtEditClientAddress.text.toString()
                        client.cp=Integer.parseInt(binding.txtEditClientPostalAddress.text.toString())
                        viewModel.updateClient(client)
                        Log.d("MHTEST", "ESTOY EN EL TRUE del formulario valido en fragment Edit Client")
                        val snackBar= Snackbar.make(binding.root,"Se modificó correctamente el cliente ${client.name}", Snackbar.LENGTH_SHORT)
                        snackBar.view.setBackgroundColor(Color.parseColor("#A9EF90"))
                        snackBar.show()
                        val navController = findNavController()
                        navController.popBackStack()
                    }
                }else{
                    setErrorMsg(
                        binding.txtEditClientName.text.toString(),
                        binding.txtEditClientCuit.text.toString(),
                        binding.txtEditClientContactPerson.text.toString(),
                        binding.txtEditClientPhone.text.toString(),
                        binding.txtEditClientEmail.text.toString(),
                        binding.txtEditClientAddress.text.toString(),
                        binding.txtEditClientPostalAddress.text.toString()
                    )
                    Log.d("MHTEST", "ESTOY EN EL false del formulario valido en fragment Edit Client")
                    val snackBar=Snackbar.make(binding.root,"ERROR:No se pudo modificar el cliente ${client.name}, revise los campos con errores", Snackbar.LENGTH_SHORT)
                    snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                    snackBar.show()
                }
            }
        }
    }


    fun setErrorMsg(name: String, cuit :String, contactPerson: String, phoneNumber: String,email: String,address: String,  postalAdress: String) {
        if (!validateViewModel.isValidName(name)) {
            binding.txtErrorClientName.text = validateViewModel.errorNombre.value.toString()
        }
        if (!validateViewModel.isValidCUIT(cuit)) {
            binding.txtErrorClientCuit.text = validateViewModel.errorCuit.value.toString()
        }
        if (!validateViewModel.isValidContactPerson(contactPerson)) {
            binding.txtErrorClientContactPerson.text = validateViewModel.errorContactPerson.value.toString()
        }
        if (!validateViewModel.isValidPhoneNumber(phoneNumber)) {
            binding.txtErrorClientPhone.text = validateViewModel.errorPhone.value.toString()
        }
        if (!validateViewModel.isValidEmail(email)) {
            binding.txtErrorClientEmail.text = validateViewModel.errorEmail.value.toString()
        }
        if (!validateViewModel.isValidAddress(address)) {
            binding.txtErrorClientAddress.text = validateViewModel.errorAddress.value.toString()
        }
        if (!validateViewModel.isValidWidth(postalAdress)) {
            binding.txtErrorClientPostalAddress.text = validateViewModel.errorPostalAddress.value.toString()
        }
    }

    fun clearErrorMsg(name: String, cuit :String, contactPerson: String, phoneNumber: String,email: String,address: String,  postalAdress: String){
        if (validateViewModel.isValidName(name)) {
            binding.txtErrorClientName.text = ""
        }
        if (validateViewModel.isValidCUIT(cuit)) {
            binding.txtErrorClientCuit.text = ""
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