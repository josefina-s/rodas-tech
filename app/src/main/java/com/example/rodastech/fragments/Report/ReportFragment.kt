package com.example.rodastech.fragments.Report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rodastech.R
import com.example.rodastech.adapters.ReportAdapter
import com.example.rodastech.entities.repositories.ReportRepository

class ReportFragment : Fragment() {

    lateinit var v:View
    lateinit var recyclerReport : RecyclerView
    private lateinit var reportRepository: ReportRepository
    lateinit var adapter : ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_report, container, false)
        val reportViewModel = ViewModelProvider(requireActivity()).get(ReportViewModel::class.java)
        reportRepository = reportViewModel.reportRepository
        recyclerReport = v.findViewById(R.id.recReportList)
        return v
    }

    override fun onStart() {
        super.onStart()
        recyclerReport.layoutManager = LinearLayoutManager(context)

        adapter = ReportAdapter(reportRepository.reports){position ->
            val action = ReportFragmentDirections.actionReportFragmentToDetailReportFragment((reportRepository.reports[position]))
            findNavController().navigate(action)
        }
        recyclerReport.adapter = adapter


    }

}