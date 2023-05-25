package com.example.rodastech.fragments.Client

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentDetailClientBinding
import com.example.rodastech.databinding.FragmentDetailClothBinding
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.example.rodastech.fragments.Cloth.DetailClothFragmentArgs
import com.example.rodastech.fragments.Cloth.DetailClothFragmentDirections
import com.example.rodastech.fragments.Cloth.DetailClothViewModel
import kotlinx.coroutines.launch

class DetailClientFragment : Fragment() {
    private lateinit var binding: FragmentDetailClientBinding
    private lateinit var builder: AlertDialog.Builder
    private val viewModel: DetailClientViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        builder= AlertDialog.Builder(activity)
        binding=FragmentDetailClientBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val args = DetailClientFragmentArgs.fromBundle(requireArguments()).client

        binding.txtNameClient.text=args.name
        binding.txtCuit.text=args.cuit
        binding.txtAddress.text= args.address
        binding.txtContactPerson.text=args.contactPerson
        binding.txtEmail.text=args.email
        binding.txtPhone.text=args.telephone.toString()
        binding.txtPostalAddress.text=args.cp.toString()

        binding.imgBtnEditClient.setOnClickListener {
            val action = DetailClientFragmentDirections.actionDetailClientFragmentToEditClientFragment(
                Client(args.id,args.name,args.cuit,args.contactPerson,args.telephone,args.email,args.address,args.cp)
            )
            findNavController().navigate(action)
        }

        binding.imgBtnDeleteClient.setOnClickListener {
            builder.setTitle("Atención!")
                .setMessage("Desea borrar el cliente?")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    viewModel.viewModelScope.launch {
                        viewModel.deleteClient(
                            Client(args.id,args.name,args.cuit,args.contactPerson,args.telephone,args.email,args.address,args.cp)
                        )
                    }
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
    }



}