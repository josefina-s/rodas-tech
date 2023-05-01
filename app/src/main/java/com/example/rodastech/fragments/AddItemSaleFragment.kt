package com.example.rodastech.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rodastech.R

class AddItemSaleFragment : Fragment() {

    companion object {
        fun newInstance() = AddItemSaleFragment()
    }

    private lateinit var viewModel: AddItemSaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item_sale, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddItemSaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}