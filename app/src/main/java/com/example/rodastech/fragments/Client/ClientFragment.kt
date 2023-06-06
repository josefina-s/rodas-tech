package com.example.rodastech.fragments.Client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rodastech.R
import com.example.rodastech.adapters.ClientAdapter
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.databinding.FragmentClientBinding
import com.example.rodastech.databinding.FragmentCreateClientBinding
import com.example.rodastech.databinding.FragmentListClothBinding
import com.example.rodastech.fragments.Cloth.ListClothFragmentDirections

class ClientFragment : Fragment() {

    private val viewModel: ClientViewModel by activityViewModels()
    private lateinit var binding: FragmentClientBinding
    lateinit var adapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentClientBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        binding.recClientList.layoutManager=LinearLayoutManager(context)
        viewModel.getClientsList()

        viewModel.dbClients.observe(viewLifecycleOwner){clients->

            adapter= ClientAdapter(clients.toMutableList()){position->
                viewModel.setSelectedClient(clients.toMutableList()[position])
                val action= ClientFragmentDirections.actionClientFragmentToDetailClientFragment()
                findNavController().navigate(action)
            }
            binding.recClientList.adapter=adapter
        }

        binding.floatActionCreateClient.setOnClickListener {
            val action= ClientFragmentDirections.actionClientFragmentToCreateClientFragment()
            findNavController().navigate(action)
        }

    }

}