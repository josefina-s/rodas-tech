package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private lateinit var binding: FragmentCreateClothBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreateClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnCreateClothSave.setOnClickListener(){
            viewModel.viewModelScope.launch {
                val myUuid = UUID.randomUUID()
                val myUuidAsString = myUuid.toString()
                viewModel.insertCloth(Cloth(myUuidAsString,
                    binding.txtCreateClothName.text.toString(),
                    binding.txtCreateClothDesc.text.toString(),
                    binding.txtCreateClothProvider.text.toString(),
                    binding.txtCreateClothColor.text.toString(),
                    Integer.parseInt(binding.txtCreateClothWidth.text.toString()),
                    Integer.parseInt(binding.txtCreateClothLong.text.toString()),
                    Integer.parseInt(binding.txtCreateClothInitialStock.text.toString())
                ))
            }
        }
    }



}