package com.example.rodastech.fragments.Client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.databinding.FragmentCreateClientBinding
import com.example.rodastech.entities.Client
import kotlinx.coroutines.launch
import java.util.*

class CreateClientFragment : Fragment() {
    private val viewModel: CreateClientViewModel by viewModels()
    private lateinit var binding: FragmentCreateClientBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCreateClientBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnSaveCreateClient.setOnClickListener{
            viewModel.viewModelScope.launch {
                val myUuid = UUID.randomUUID()
                val myUuidAsString = myUuid.toString()
                viewModel.insertClient(
                    Client(myUuidAsString,
                    binding.txtCreateClientName.text.toString(),
                    binding.txtCreateClientCuit.text.toString(),
                    binding.txtCreateClientContactPerson.text.toString(),
                    Integer.parseInt(binding.txtCreateClientPhone.text.toString()),
                    binding.txtCreateClientEmail.text.toString(),
                    binding.txtCreateClientAddress.text.toString(),
                    Integer.parseInt(binding.txtCreteClientPostalAddress.text.toString())
                )
                )
            }
        }
    }



}