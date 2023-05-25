package com.example.rodastech.fragments.Cloth

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.rodastech.R
import com.example.rodastech.databinding.FragmentCreateClothBinding
import com.example.rodastech.databinding.FragmentDeleteClothBinding
//import com.example.rodastech.fragments.DeleteClothFragmentArgs
import com.google.android.material.snackbar.Snackbar

class DeleteClothFragment : Fragment() {

    private val viewModel: DeleteClothViewModel by viewModels()
    private lateinit var binding: FragmentDeleteClothBinding
    private lateinit var builder: AlertDialog.Builder



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        builder=AlertDialog.Builder(activity)
        binding=FragmentDeleteClothBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onStart() {
        super.onStart()
//
//        btnDelete.setOnClickListener(){
//
//        }


    }


}