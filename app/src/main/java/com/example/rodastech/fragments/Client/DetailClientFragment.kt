package com.example.rodastech.fragments.Client

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.databinding.FragmentDetailClientBinding
import com.example.rodastech.entities.Client
import kotlinx.coroutines.launch

class DetailClientFragment : Fragment() {
    private lateinit var binding: FragmentDetailClientBinding
    private lateinit var builder: AlertDialog.Builder
    private val viewModel: DetailClientViewModel by activityViewModels()
    private val clientViewModel: ClientViewModel by activityViewModels()
    lateinit var client: Client



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        client=clientViewModel.selectedClient.value!!
        builder= AlertDialog.Builder(activity)
        binding=FragmentDetailClientBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController()

        binding.txtNameClient.text=client.name
        binding.txtCuit.text=client.cuit
        binding.txtAddress.text= client.address
        binding.txtContactPerson.text=client.contactPerson
        binding.txtEmail.text=client.email
        binding.txtPhone.text=client.telephone.toString()
        binding.txtPostalAddress.text=client.cp.toString()


        binding.imgBtnEditClient.setOnClickListener {
            val action = DetailClientFragmentDirections.actionDetailClientFragmentToEditClientFragment()
            findNavController().navigate(action)
        }

        binding.imgBtnDeleteClient.setOnClickListener {
            builder.setTitle("Atención!")
                .setMessage("Desea borrar el cliente ${client.name}?")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    viewModel.viewModelScope.launch {
                        viewModel.deleteClient(client)
                        navController.popBackStack()
                        Toast.makeText(requireContext(), "Se eliminó el cliente: ${client.name}", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
    }



}