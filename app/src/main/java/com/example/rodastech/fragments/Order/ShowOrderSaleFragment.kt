package com.example.rodastech.fragments.Order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rodastech.R

class ShowOrderSaleFragment : Fragment() {

    companion object {
        fun newInstance() = ShowOrderSaleFragment()
    }

    private lateinit var viewModel: ShowOrderSaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_order_sale, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowOrderSaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}