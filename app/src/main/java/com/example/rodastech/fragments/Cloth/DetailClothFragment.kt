package com.example.rodastech.fragments.Cloth

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.rodastech.R
import com.example.rodastech.activities.MainActivity
import com.example.rodastech.adapters.ClothAdapter
import com.example.rodastech.databinding.FragmentDeleteClothBinding
import com.example.rodastech.databinding.FragmentDetailClothBinding
import com.example.rodastech.databinding.FragmentListClothBinding
import com.example.rodastech.entities.Cloth
import kotlinx.coroutines.launch

class DetailClothFragment : Fragment() {

    lateinit var v : View
    private lateinit var binding: FragmentDetailClothBinding
    private lateinit var builder: Builder
    private val viewModel: DetailClothViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        builder= Builder(activity)
        binding=FragmentDetailClothBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val args =DetailClothFragmentArgs.fromBundle(requireArguments()).cloth

        binding.txtNombre.text=args.name
        binding.txtColor.text=args.color
        binding.txtAncho.text= args.width.toString()
        binding.txtLargo.text=args.long.toString()
        binding.txtPrecio.text=args.price.toString()
        binding.txtProveedor.text=args.provider
        binding.txtMinStock.text=args.stockMinimo.toString()
        binding.txtStock.text=args.stockActual.toString()

        binding.imgBtnEditCloth.setOnClickListener {
            val action = DetailClothFragmentDirections.actionDetailClothFragmentToEditClothFragment(
                Cloth(args.id,args.name,args.description,args.provider,args.color,args.width,args.long,args.meters,args.price,args.stockActual,args.stockMinimo)
            )
            findNavController().navigate(action)
        }

        binding.imgBtnDeleteCloth.setOnClickListener {
            builder.setTitle("Atención!")
                .setMessage("Desea borrar el producto?")
                .setCancelable(true)
                .setPositiveButton("Si"){dialogInterface, it ->
                    viewModel.viewModelScope.launch {
                        viewModel.deleteCloth(Cloth(args.id,
                            args.name,
                            args.description,
                            args.provider,
                            args.color,
                            args.width,
                            args.long,
                            args.meters,
                            args.price,
                            args.stockActual,
                            args.stockMinimo))
                    }
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
    }

}
