package com.example.rodastech.entities.repositories

import com.example.rodastech.entities.Report

class ReportRepository() {
    var reports: MutableList<Report> = mutableListOf()

    init {
        reports.add(Report("Telas por stock"))
        reports.add(Report("Telas por precio"))
    }

}