package com.example.rodastech.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.rodastech.R
import com.google.android.material.snackbar.Snackbar

class EditClothFragment : Fragment() {
    lateinit var v : View
    lateinit var txtName : TextView
    lateinit var txtMeters : TextView
    lateinit var txtPrice : TextView
    lateinit var btnSave : Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_edit_cloth, container, false)
        txtName=v.findViewById(R.id.editTextClothName)
        txtMeters=v.findViewById(R.id.editTextClothMeters)
        txtPrice=v.findViewById(R.id.editTextClothPrice)
        btnSave=v.findViewById(R.id.btnSave)
        return v
    }



    override fun onStart() {
        super.onStart()
        val args= EditClothFragmentArgs.fromBundle(requireArguments()).cloth
        txtName.text=args.name
        txtMeters.text=args.meters
        txtPrice.text= args.price.toString()


        btnSave.setOnClickListener(){
            //AGREGAR VALIDACIONES PARA VER SI LA MODIFICACION ES EXITOSA O NO --mheredia 20230430
            args.name=txtName.text.toString()
            args.meters=txtMeters.text.toString()
            args.price= Integer.parseInt(txtPrice.text.toString())
            val snackBar= Snackbar.make(v,"Modificación exitosa", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.parseColor("#57E049"))
            snackBar.show()
        }


    }


//    companion object {
//        fun newInstance() = EditClothFragment()
//    }
//
//    private lateinit var viewModel: EditClothViewModel
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EditClothViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
}