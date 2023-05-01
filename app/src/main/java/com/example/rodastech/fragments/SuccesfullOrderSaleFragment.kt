package com.example.rodastech.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rodastech.R

class SuccesfullOrderSaleFragment : Fragment() {

    companion object {
        fun newInstance() = SuccesfullOrderSaleFragment()
    }

    private lateinit var viewModel: SuccesfullOrderSaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_succesfull_order_sale, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuccesfullOrderSaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}