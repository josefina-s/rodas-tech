package com.example.rodastech.fragments.Cloth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.entities.repositories.ClothRepository
//import com.example.rodastech.fragments.ListClothFragmentDirections

class ListClothFragment : Fragment() {

    lateinit var v:View
    lateinit var recyclerCloth : RecyclerView
    var clothRepository : ClothRepository = ClothRepository()
    lateinit var adapter : ClothAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_cloth, container, false)
        recyclerCloth = v.findViewById(R.id.recList)
        return v
    }

    override fun onStart() {
        super.onStart()
        recyclerCloth.layoutManager = LinearLayoutManager(context)
//      PARA PROBAR LOS MÉTODOS DEL CRUD DESCOMENTAR SEGÚN EL MÉTODO QUE SE NECESITE mheredia 20230430
//        adapter = ClothAdapter(clothRepository.cloths){position->
////            val action = ListClothFragmentDirections.actionListClothFragmentToDetailClothFragment(clothRepository.cloths[position])
////            val actionEdit = ListClothFragmentDirections.actionListClothFragmentToEditClothFragment(clothRepository.cloths[position])
//            findNavController().navigate(actionDelete)
//        }

        adapter = ClothAdapter(clothRepository.cloths){position->
            val actionDelete =
                ListClothFragmentDirections.actionListClothFragmentToDeleteClothFragment(
                    clothRepository.cloths[position],
                    clothRepository.cloths.toTypedArray()
                )
            findNavController().navigate(actionDelete)
        }


//        adapter = ClothAdapter(clothRepository.cloths){
//            val actionCreate= ListClothFragmentDirections.actionListClothFragmentToCreateClothFragment4(clothRepository.cloths.toTypedArray())
//            findNavController().navigate(actionCreate)
//            adapter.notifyDataSetChanged()
//        }
        recyclerCloth.adapter = adapter

    }


}