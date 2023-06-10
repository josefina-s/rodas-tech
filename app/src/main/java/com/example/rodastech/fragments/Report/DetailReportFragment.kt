package com.example.rodastech.fragments.Report

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth
import com.example.rodastech.fragments.Cloth.ListClothViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class DetailReportFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var viewModel: DetailReportViewModel
    private lateinit var v :View
    private lateinit var barChart : BarChart
    private val viewModelCloth: ListClothViewModel by viewModels()
    private var mutableCloths: MutableList<Cloth> = mutableListOf()
    private val entries = mutableListOf<BarEntry>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detail_report, container, false)
        barChart = v.findViewById(R.id.chart)
        return v
    }

    override fun onStart() {
        super.onStart()
        val args = DetailReportFragmentArgs.fromBundle(requireArguments()).report.name
        viewModelCloth.llamarGetAllCloths()

        viewModelCloth.cloths.observe(viewLifecycleOwner) { cloths ->
            mutableCloths = cloths.toMutableList()


            when {
                args.equals("Telas por stock") -> {
                    mutableCloths.sortByDescending { it.stockActual}
                    transformStockToChart(entries)
                }
                args.equals("Telas por precio") -> {
                    mutableCloths.sortByDescending { it.price }
                    transformPriceToChart(entries)

                }
                else -> {
                }
            }

            val barDataSet = BarDataSet(entries, args)
            barDataSet.color = resources.getColor(R.color.purple_500)
            val barData = BarData(barDataSet)
            makeVisualTransforms(barData)
        }


    }

    private fun transformStockToChart(entries: MutableList<BarEntry>) {
        entries.clear()
        mutableCloths.forEachIndexed { index, cloth ->
            val stock = cloth.stockActual?.toFloat() ?: 0f
            val entry = BarEntry((index + 1).toFloat(), stock)
            entries.add(entry)
        }
    }

    private fun transformPriceToChart(entries: MutableList<BarEntry>) {
        entries.clear()
        mutableCloths.forEachIndexed { index, cloth ->
            val stock = cloth.price?.toFloat() ?: 0f
            val entry = BarEntry((index + 1).toFloat(), stock)
            entries.add(entry)
        }
    }
    private fun makeVisualTransforms(barData: BarData) {
        barChart.apply {
            data = barData
            legend.textSize = 20f
            legend.yOffset = 20f
            setScaleEnabled(false)
            setPinchZoom(false)
            setDoubleTapToZoomEnabled(false)
            description.isEnabled = false
            setFitBars(true)
            animateY(1000)
            setOnChartValueSelectedListener(this@DetailReportFragment)
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailReportViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // Sobrescribir el método que se ejecuta cuando se selecciona una barra
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        // Obtener el índice y el valor de la barra seleccionada
        val index = e?.x?.toInt() ?: 0
        val value = e?.y ?: 0f

        // Obtener el nombre del item correspondiente al índice de la barra
        val name = mutableCloths.getOrNull(index - 1)?.name ?: ""

        // Mostrar un mensaje con el nombre y el valor del item
        val toast = Toast.makeText(requireContext(), "$name: $value", Toast.LENGTH_SHORT)
        toast.show()
        Handler().postDelayed({ toast.cancel() }, 1000)
    }

    // Sobrescribir el método que se ejecuta cuando no se selecciona ninguna barra
    override fun onNothingSelected() {
        // No hacer nada
    }
}

