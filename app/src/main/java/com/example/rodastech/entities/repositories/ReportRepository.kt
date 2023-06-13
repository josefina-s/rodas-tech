package com.example.rodastech.entities.repositories

import com.example.rodastech.entities.Report

class ReportRepository() {
    var reports: MutableList<Report> = mutableListOf()

    init {
        reports.add(Report("Telas por stock"))
        reports.add(Report("Telas por precio"))
        reports.add(Report("Telas por color"))
        reports.add(Report("Telas por tamaño"))
        reports.add(Report("Clientes con más pedidos"))

    }

}