package com.example.rodastech.fragments.Client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rodastech.adapters.ClientAdapter
import com.example.rodastech.databinding.FragmentClientBinding
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth

class ClientFragment : Fragment() {

    private val viewModel: ClientViewModel by activityViewModels()
    private lateinit var binding: FragmentClientBinding
    lateinit var adapter: ClientAdapter
    lateinit var filterList: MutableList<Client>

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
            filterList=clients.toMutableList()

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


        binding.searchClient.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Acciones a realizar cuando se envía la búsqueda
                Log.d("mhtest", query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Acciones a realizar cuando el texto de búsqueda cambia
                val newLista=filterList.filter { cloth-> cloth.name!!.lowercase().contains(newText.toString().lowercase())}
                adapter.updateClientList(newLista.toMutableList())
                binding.recClientList.adapter=adapter
                Log.d("mhtest", newText.toString())
                return true
            }
        })

    }

}