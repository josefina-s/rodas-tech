package com.example.rodastech.fragments.Cloth

import android.app.AlertDialog.Builder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.databinding.FragmentDetailClothBinding
import com.example.rodastech.entities.Cloth
import kotlinx.coroutines.launch

class DetailClothFragment : Fragment() {

    lateinit var v : View
    private lateinit var binding: FragmentDetailClothBinding
    private lateinit var builder: Builder
    private val listViewModel: ListClothViewModel by activityViewModels()
    private val viewModel: DetailClothViewModel by viewModels()
    lateinit var cloth: Cloth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cloth= listViewModel.selectedCloth.value!!
        builder= Builder(activity)
        binding=FragmentDetailClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController()
        binding.txtNombre.text=cloth.name
        binding.txtColor.text=cloth.color
        binding.txtAncho.text= cloth.width.toString()
        binding.txtLargo.text=cloth.long.toString()
        binding.txtPrecio.text=cloth.price.toString()
        binding.txtProveedor.text=cloth.provider
        binding.txtMinStock.text=cloth.stockMinimo.toString()
        binding.txtStock.text=cloth.stockActual.toString()

        binding.imgBtnEditCloth.setOnClickListener {
            val action = DetailClothFragmentDirections.actionDetailClothFragmentToEditClothFragment()
            findNavController().navigate(action)
        }

        binding.imgBtnDeleteCloth.setOnClickListener {
            builder.setTitle("Atención!")
                .setMessage("Desea borrar el producto  ${cloth.name}?")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    viewModel.viewModelScope.launch {
                        viewModel.deleteCloth(cloth)
                        navController.popBackStack()
                        Toast.makeText(requireContext(), "Se eliminó el producto: ${cloth.name}", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
    }

}
