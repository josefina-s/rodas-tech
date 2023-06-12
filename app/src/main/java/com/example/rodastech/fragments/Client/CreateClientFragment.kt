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
import com.example.rodastech.databinding.FragmentCreateClientBinding
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.example.rodastech.fragments.Cloth.ValidateFormViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.*

class CreateClientFragment : Fragment() {
    private val viewModel: CreateClientViewModel by viewModels()
    private val validateViewModel: ValidateFormViewModel by activityViewModels()
    private val listViewModel: ClientViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateClientBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnSaveCreateClient.setOnClickListener {
            clearErrorMsg(
                binding.txtCreateClientName.text.toString(),
                binding.txtCreateClientCuit.text.toString(),
                binding.txtCreateClientContactPerson.text.toString(),
                binding.txtCreateClientPhone.text.toString(),
                binding.txtCreateClientEmail.text.toString(),
                binding.txtCreateClientAddress.text.toString(),
                binding.txtCreteClientPostalAddress.text.toString()
            )
            clearExistsErrorMsg()
            validateViewModel.validarFormulario(
                binding.txtCreateClientName.text.toString(),
                binding.txtCreateClientCuit.text.toString(),
                binding.txtCreateClientContactPerson.text.toString(),
                binding.txtCreateClientPhone.text.toString(),
                binding.txtCreateClientEmail.text.toString(),
                binding.txtCreateClientAddress.text.toString(),
                binding.txtCreteClientPostalAddress.text.toString()
            )
            if (!clientExists(
                    binding.txtCreateClientName.text.toString(),
                    binding.txtCreateClientCuit.text.toString()
                )
            ) {
                validateViewModel.formularioValido.observe(viewLifecycleOwner) {
                    if (it) {
                        viewModel.viewModelScope.launch {
                            val myUuid = UUID.randomUUID()
                            val myUuidAsString = myUuid.toString()
                            val client = Client(
                                myUuidAsString,
                                binding.txtCreateClientName.text.toString().capitalize(),
                                binding.txtCreateClientCuit.text.toString(),
                                binding.txtCreateClientContactPerson.text.toString().capitalize(),
                                Integer.parseInt(binding.txtCreateClientPhone.text.toString()),
                                binding.txtCreateClientEmail.text.toString(),
                                binding.txtCreateClientAddress.text.toString().capitalize(),
                                Integer.parseInt(binding.txtCreteClientPostalAddress.text.toString())
                            )
                            viewModel.insertClient(client)
                            Log.d(
                                "MHTEST",
                                "ESTOY EN EL TRUE del formulario valido en fragment Create Client"
                            )
                            val snackBar = Snackbar.make(
                                binding.root,
                                "Se agregó correctamente el cliente ${client.name}",
                                Snackbar.LENGTH_SHORT
                            )
                            snackBar.view.setBackgroundColor(Color.parseColor("#33363F"))
                            snackBar.show()
                            val navController = findNavController()
                            navController.popBackStack()
                        }

                    } else {
                        setErrorMsg(
                            binding.txtCreateClientName.text.toString(),
                            binding.txtCreateClientCuit.text.toString(),
                            binding.txtCreateClientContactPerson.text.toString(),
                            binding.txtCreateClientPhone.text.toString(),
                            binding.txtCreateClientEmail.text.toString(),
                            binding.txtCreateClientAddress.text.toString(),
                            binding.txtCreteClientPostalAddress.text.toString()
                        )
                        Log.d(
                            "MHTEST",
                            "ESTOY EN EL false del formulario validar en fragment create client"
                        )
                        val snackBar = Snackbar.make(
                            binding.root,
                            "ERROR:No se pudo guardar el cliente, revise los campos con errores",
                            Snackbar.LENGTH_SHORT
                        )
                        snackBar.view.setBackgroundColor(Color.parseColor("#DD5050"))
                        snackBar.show()
                    }
                }
            } else {
                setExistsErrorMsg()
            }

        }
    }

    fun setErrorMsg(
        name: String,
        cuit: String,
        contactPerson: String,
        phoneNumber: String,
        email: String,
        address: String,
        postalAdress: String
    ) {
        if (!validateViewModel.isValidName(name)) {
            binding.txtErrorClientName.text = validateViewModel.errorNombre.value.toString()
        }
        if (!validateViewModel.isValidCUIT(cuit)) {
            binding.txtErrorClientCuit.text = validateViewModel.errorCuit.value.toString()
        }
        if (!validateViewModel.isValidContactPerson(contactPerson)) {
            binding.txtErrorClientContactPerson.text =
                validateViewModel.errorContactPerson.value.toString()
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
            binding.txtErrorClientPostalAddress.text =
                validateViewModel.errorPostalAddress.value.toString()
        }
    }

    fun clearErrorMsg(
        name: String,
        cuit: String,
        contactPerson: String,
        phoneNumber: String,
        email: String,
        address: String,
        postalAdress: String
    ) {
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

    fun setExistsErrorMsg(
    ) {
        binding.txtErrorClientName.text = "Ya existe un cliente con ese nombre registrado"
        binding.txtErrorClientCuit.text =
            "Ya existe un cliente con ese CUIT registrado"
    }

    fun clearExistsErrorMsg(
    ) {
        binding.txtErrorClientCuit.text = ""
        binding.txtErrorClientCuit.text = ""
    }

    fun clientExists(name: String, cuit: String): Boolean {
        var filterList: MutableList<Client>
        val cuitLimpio = cuit.replace("-", "")
        filterList = listViewModel.dbClients.value!!.filter { client ->
            client.name!!.lowercase().contains(name) && client.cuit!!.replace("-", "")
                .contains(cuit)
        } as MutableList<Client>
        if (filterList.isNullOrEmpty()) {
            return false
        } else {
            return true
        }
    }


}