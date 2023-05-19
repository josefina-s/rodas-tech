package com.example.rodastech.fragments.Client

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentEditClientBinding
import com.example.rodastech.databinding.FragmentEditClothBinding
import com.example.rodastech.fragments.Cloth.EditClothFragmentArgs
import com.example.rodastech.fragments.Cloth.EditClothViewModel
import kotlinx.coroutines.launch

class EditClientFragment : Fragment() {
    private val viewModel: EditClientViewModel by viewModels()
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
            viewModel.viewModelScope.launch{
                args.name=binding.txtEditClientName.text.toString()
                args.cuit=binding.txtEditClientCuit.text.toString()
                args.contactPerson=binding.txtEditClientContactPerson.text.toString()
                args.telephone=Integer.parseInt(binding.txtEditClientPhone.text.toString())
                args.email=binding.txtEditClientEmail.text.toString()
                args.address=binding.txtEditClientAddress.text.toString()
                args.cp=Integer.parseInt(binding.txtEditClientPostalAddress.text.toString())
                viewModel.updateClient(args)
            }
        }


    }


}