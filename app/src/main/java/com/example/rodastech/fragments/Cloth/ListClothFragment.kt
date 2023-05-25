package com.example.rodastech.fragments.Cloth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.databinding.FragmentListClothBinding


class ListClothFragment : Fragment() {

    lateinit var adapter : ClothAdapter
    private val viewModel: ListClothViewModel by viewModels()
    private lateinit var binding: FragmentListClothBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentListClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.recList.layoutManager=LinearLayoutManager(context)
        viewModel.llamarGetAllCloths()

        viewModel.cloths.observe(viewLifecycleOwner){cloths->
            cloths.toMutableList()
            adapter = ClothAdapter(cloths.toMutableList()){position->
                val action = ListClothFragmentDirections.actionListClothFragmentToDetailClothFragment(cloths.toMutableList()[position])
                findNavController().navigate(action)
            }
            binding.recList.adapter=adapter
        }
        binding.floatActionCreateCloth.setOnClickListener(){
            val action = ListClothFragmentDirections.actionListClothFragmentToCreateClothFragment()
            findNavController().navigate(action)
        }

    }


}