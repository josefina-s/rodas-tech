package com.example.rodastech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.entities.Client

class ClientAdapter (
    var clientList : MutableList<Client>,
    var onClick: (Int) -> Unit
        ): RecyclerView.Adapter<ClientAdapter.ClientHolder>(){

            class ClientHolder(v: View) : RecyclerView.ViewHolder(v){
                private var v = v
                init {
                    this.v=v
                }
                fun setName(name: String){
                    val txtName: TextView = v.findViewById(R.id.txtClientName)
                    txtName.text = name
                }

                fun getCardImgBtnDetails(): ImageButton {
                    val btnDetail : ImageButton = v.findViewById(R.id.imgBtnClientDetails)
                    return btnDetail
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return ClientHolder(view)
    }

    override fun getItemCount(): Int {
        return clientList.size
    }

    override fun onBindViewHolder(holder: ClientHolder, position: Int) {
//        clientList[position].name?.let { holder.setName(it) }

        holder.getCardImgBtnDetails().setOnClickListener {
            onClick(position)
        }
    }


}