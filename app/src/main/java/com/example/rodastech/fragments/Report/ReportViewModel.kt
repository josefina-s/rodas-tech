package com.example.rodastech.fragments.Report

import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.repositories.ReportRepository

class ReportViewModel : ViewModel() {
    val reportRepository: ReportRepository = ReportRepository()
}