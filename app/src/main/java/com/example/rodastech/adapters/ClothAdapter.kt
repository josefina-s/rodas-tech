package com.example.rodastech.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth

class ClothAdapter (
    var clothList : MutableList<Cloth>,
    var onClick: (Int) -> Unit
): RecyclerView.Adapter<ClothAdapter.ClothHolder>() {


    class ClothHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var v = v

        init {
            this.v = v

        }

        fun setName(name: String) {
            val txtName: TextView = v.findViewById(R.id.txtName)
            txtName.text = name
        }

        fun setCantMtrs(meters: Int?) {
            val txtMeters: TextView = v.findViewById(R.id.txtMeters)
            txtMeters.text = meters.toString()
        }

        fun getCard(): CardView {
            return v.findViewById(R.id.clothCard)
        }
        fun getCardImgBtnAdd(): ImageButton {
            val btnAdd : ImageButton= v.findViewById(R.id.imgBtnAddQuantity)
            return btnAdd
        }
        fun getCardImgBtnDetails(): ImageButton {
            val btnDetail : ImageButton= v.findViewById(R.id.imgBtnDetails)
            return btnDetail
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cloth, parent, false)
        return ClothHolder(view)
    }

    override fun getItemCount(): Int {
        return clothList.size
    }


    override fun onBindViewHolder(holder: ClothHolder, position: Int) {
        clothList[position].name?.let { holder.setName(it) }
        holder.setCantMtrs(clothList[position].meters)

        holder.getCardImgBtnAdd().setOnClickListener {
            Log.d("MHTEST","HOLA")
        }
        holder.getCardImgBtnDetails().setOnClickListener {
            onClick(position)
        }

    }
}