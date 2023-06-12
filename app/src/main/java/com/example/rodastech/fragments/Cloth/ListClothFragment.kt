package com.example.rodastech.fragments.Cloth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rodastech.activities.SignInActivity
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.databinding.FragmentListClothBinding
import com.example.rodastech.entities.Cloth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ListClothFragment  : Fragment()  {

    lateinit var adapter : ClothAdapter
    private val viewModel: ListClothViewModel by activityViewModels()
    private lateinit var binding: FragmentListClothBinding
    lateinit var filterList: MutableList<Cloth>


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
            filterList=cloths.toMutableList()
            adapter = ClothAdapter(cloths.toMutableList()){position, buttonId->

                when(buttonId){
                    "ADD" ->{
                        viewModel.setSelectedCloth(cloths[position])
                        val dialogFragment = AddStockDialog()
                        dialogFragment.show(parentFragmentManager, "AddStockDialog")
                    }
                    "DETAILS"->{
                        viewModel.setSelectedCloth(cloths[position])
                        val action = ListClothFragmentDirections.actionListClothFragmentToDetailClothFragment()
                        findNavController().navigate(action)
                    }
                }
            }
            binding.recList.adapter=adapter
        }
        binding.searchCloth.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("mhtest", query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newLista=filterList.filter { cloth-> cloth.name!!.lowercase().contains(newText.toString().lowercase())}
                adapter.updateClothList(newLista.toMutableList())
                binding.recList.adapter=adapter
                Log.d("mhtest", newText.toString())
                return true
            }
        })
        binding.floatActionCreateCloth.setOnClickListener(){
            val action = ListClothFragmentDirections.actionListClothFragmentToCreateClothFragment()
            findNavController().navigate(action)
        }

        binding.btnLogout.setOnClickListener{
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), "Deslogueo exitoso!", Toast.LENGTH_LONG).show()
        }

    }





}