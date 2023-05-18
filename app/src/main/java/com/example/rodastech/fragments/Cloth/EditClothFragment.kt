package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentDetailClothBinding
import com.example.rodastech.databinding.FragmentEditClothBinding
//import com.example.rodastech.fragments.EditClothFragmentArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditClothFragment : Fragment() {
    private val viewModel: EditClothViewModel by viewModels()
    private lateinit var binding: FragmentEditClothBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentEditClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val args= EditClothFragmentArgs.fromBundle(requireArguments()).cloth
        binding.txtEditClothName.setText(args.name)
        binding.txtEditClothDesc.setText(args.description)
        binding.txtEditClothColor.setText(args.color)
        binding.txtEditClothWidth.setText(args.width.toString())
        binding.txtEditClothLong.setText(args.long.toString())
        binding.txtEditClothProvider.setText(args.provider)
        binding.txtEditClothStock.setText(args.stockActual.toString())


        binding.btnSave.setOnClickListener(){
            viewModel.viewModelScope.launch{
                args.name=binding.txtEditClothName.text.toString()
                args.description=binding.txtEditClothDesc.text.toString()
                args.color=binding.txtEditClothColor.text.toString()
                args.width=Integer.parseInt(binding.txtEditClothWidth.text.toString())
                args.long=Integer.parseInt(binding.txtEditClothLong.text.toString())
                args.provider=binding.txtEditClothProvider.text.toString()
                args.stockActual=Integer.parseInt(binding.txtEditClothStock.text.toString())
                viewModel.updateCloth(args)
            }
        }


    }

}