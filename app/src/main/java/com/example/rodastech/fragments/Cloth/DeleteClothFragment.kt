package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.rodastech.R
//import com.example.rodastech.fragments.DeleteClothFragmentArgs
import com.google.android.material.snackbar.Snackbar

class DeleteClothFragment : Fragment() {

    lateinit var v : View
    lateinit var txtName : TextView
    lateinit var txtMeters : TextView
    lateinit var txtPrice : TextView
    lateinit var btnDelete : Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_delete_cloth, container, false)
        txtName=v.findViewById(R.id.deleteTextName)
        txtMeters=v.findViewById(R.id.deleteTextMeters)
        txtPrice=v.findViewById(R.id.deleteTextPrice)
        btnDelete=v.findViewById(R.id.btnDelete)
        return  v
    }



    override fun onStart() {
        super.onStart()
        val cloth= DeleteClothFragmentArgs.fromBundle(requireArguments()).cloth
        val args= DeleteClothFragmentArgs.fromBundle(requireArguments()).clothArray.toMutableList()
        txtName.text=cloth.name
        txtMeters.text=cloth.meters
        txtPrice.text= cloth.price.toString()


        btnDelete.setOnClickListener(){
            //AGREGAR VALIDACIONES PARA VER SI LA ELIMINACION ES EXITOSA O NO --mheredia 20230430
            args.removeAt(cloth.id-1)
            val snackBar= Snackbar.make(v,"ELIMINACIÓN exitosa", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.parseColor("#57E049"))
            snackBar.show()
        }


    }





//    companion object {
//        fun newInstance() = DeleteClothFragment()
//    }
//
//    private lateinit var viewModel: DeleteClothViewModel
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(DeleteClothViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}