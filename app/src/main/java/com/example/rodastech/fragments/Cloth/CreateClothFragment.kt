package com.example.rodastech.fragments.Cloth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth
//import com.example.rodastech.fragments.CreateClothFragmentArgs
import com.google.android.material.snackbar.Snackbar

class CreateClothFragment : Fragment() {
    lateinit var v : View
    lateinit var txtName : EditText
    lateinit var txtMeters : EditText
    lateinit var txtPrice : EditText
    lateinit var inputName : String
    lateinit var inputMeters : String
    var inputPrice : Int = 0
    lateinit var btnCreate : Button

//    lateinit var adapter : ClothAdapter
//    var clothRepository : ClothRepository = ClothRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_create_cloth, container, false)
        txtName=v.findViewById(R.id.createTextName)
        txtMeters=v.findViewById(R.id.createTextMeters)
        txtPrice=v.findViewById(R.id.createTextPrice)
        btnCreate=v.findViewById(R.id.btnCreate)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnCreate.setOnClickListener(){
            //AGREGAR VALIDACIONES PARA VER SI LA CREACION ES EXITOSA O NO --mheredia 20230430
            val args= CreateClothFragmentArgs.fromBundle(requireArguments()).clothArray.toMutableList()

            inputName=txtName.text.toString()
            inputMeters=txtMeters.text.toString()
            inputPrice= Integer.parseInt(txtPrice.text.toString())
            val i=args.lastIndex+2
            args.add(Cloth(i, inputName,inputMeters,inputPrice))
            val snackBar= Snackbar.make(v,"Creacion exitosa", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.parseColor("#57E049"))
            snackBar.show()

//            adapter = ClothAdapter(args){
//                adapter.notifyItemInserted(i)
//                adapter.notifyDataSetChanged()
//            }
//            adapter.notifyItemInserted(i)
//            adapter.notifyDataSetChanged()
        }


    }

//    companion object {
//        fun newInstance() = CreateClothFragment()
//    }
//
//    private lateinit var viewModel: CreateClothViewModel
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(CreateClothViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}