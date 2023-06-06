package com.example.rodastech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.entities.Report

class ReportAdapter (
    var reportList : MutableList<Report>,
    var onClick: (Int) -> Unit
): RecyclerView.Adapter<ReportAdapter.ReportHolder>() {


    class ReportHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var v = v

        init {
            this.v = v
        }

        fun setName(name: String) {
            val txtName: TextView = v.findViewById(R.id.txtNameReport)
            txtName.text = name
        }

        fun getCard(): CardView {
            return v.findViewById(R.id.reportCard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return ReportHolder(view)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportHolder, position: Int) {
        holder.setName(reportList[position].name)

        holder.getCard().setOnClickListener {
            onClick(position)
        }
    }




}