package com.example.rodastech.fragments.Report

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.rodastech.R
import com.example.rodastech.entities.Cloth
import com.example.rodastech.entities.ProductoPedido
import com.example.rodastech.fragments.Cloth.ListClothViewModel
import com.example.rodastech.fragments.Order.PedidosListViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.launch
import java.util.*

class DetailReportFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var viewModel: DetailReportViewModel
    private lateinit var v: View
    private lateinit var barChart: BarChart
    private val viewModelCloth: ListClothViewModel by viewModels()
    private val viewModelOrder: PedidosListViewModel by viewModels()
    private var mutableCloths: MutableList<Cloth> = mutableListOf()
    private var mutableProductoPedido: MutableList<ProductoPedido> = mutableListOf()
    private var mutableClient: MutableList<String> = mutableListOf()
    private val entries = mutableListOf<BarEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detail_report, container, false)
        barChart = v.findViewById(R.id.barChart)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModels()
        setupChart()
        fetchReportData()

    }

    private fun initializeViewModels() {
        viewModel = ViewModelProvider(this).get(DetailReportViewModel::class.java)
    }

    private fun setupChart() {
        barChart.apply {
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

    private fun fetchReportData() {
        val args = DetailReportFragmentArgs.fromBundle(requireArguments()).report.name
        viewModelCloth.llamarGetAllCloths()
        viewModelOrder.getAllPedidos()
        viewModelCloth.cloths.observe(viewLifecycleOwner) { cloths ->
            mutableCloths = cloths.toMutableList()

            when {
                args.equals("Telas por stock") -> {
                    mutableCloths.sortByDescending { it.stockActual }
                    transformToChart(entries, "stock")
                }
                args.equals("Telas por precio") -> {
                    mutableCloths.sortByDescending { it.price }
                    transformToChart(entries, "price")
                }
                args.equals("Telas por color") -> {
                    val grouped = mutableCloths.groupBy { it.color }
                        .map { (color, clothList) -> Pair(color, clothList.size) }
                    val sorted = grouped.sortedByDescending { it.second }
                    transformToChart(entries, sorted)
                }
                args.equals("Telas por tamaño") -> {
                    mutableCloths.sortByDescending { it.width?.times(it.long!!) }
                    transformToChart(entries, "size")
                }
                else -> {
                }

            }
            setupBarChart(entries, args)

        }

        viewModelOrder.viewModelScope.launch {
            val listaProductosPedidos = viewModelOrder.pGetAllProductosPedidos()

            when{
                args.equals("Productos más pedidos") -> {
                    mutableProductoPedido = getAllProductosPedidosAgrupados(listaProductosPedidos)
                    val sorted = mutableProductoPedido.sortedByDescending { it.metros }
                    transformToChart(entries, sorted)
                }
                args.equals("Clientes con más pedidos") -> {
                    val listaPedidos = viewModelOrder.pGetAllPedidos()
                    val mejoresClientes = listaPedidos.groupingBy { it.cliente }.eachCount()

                    val sorted = mejoresClientes.toList()
                        .sortedByDescending { it.second }
                    mutableClient = sorted.map { it.first } as MutableList<String>
                    transformToChart(entries,sorted)
                }
            }
            setupBarChart(entries, args)
        }



    }

    private fun setupBarChart(entries: List<BarEntry>, label: String) {
        val barDataSet = BarDataSet(entries, label)

        // Configurar colores de las barras
        val colors = mutableListOf<Int>()
        if (label == "Telas por color") {
            for (i in 0 until entries.size) {
                val cloth = mutableCloths.getOrNull(i)
                val color = getColorForCloth(cloth)
                colors.add(color)
            }
        } else {
            for (i in 0 until entries.size) {
                val color = getRandomColor()
                colors.add(color)
            }
        }
        barDataSet.colors = colors

        // Configurar etiquetas de las barras
        barDataSet.valueTextSize = 12f
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry): String {
                return barEntry.y.toInt().toString() // Solo el número
            }
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.animateY(1000) // Duración de la animación en milisegundos
        barChart.animateXY(1000, 1000) // Duración de la animación en milisegundos para el eje X y el eje Y respectivamente
        barChart.highlightValue(2f, 0, 500) // Resalta la barra con el valor x = 2, en el conjunto de datos 0, durante 500 milisegundos

        barChart.invalidate()

    }


    private fun getColorForCloth(cloth: Cloth?): Int {
        // Asignar un color específico para cada tela según sus propiedades
        return when (cloth?.color?.lowercase()) {
            "rojo" -> Color.RED
            "azul" -> Color.BLUE
            "verde" -> Color.GREEN
            "azul oscuro" -> Color.rgb(0, 91, 159)
            "beige" -> Color.parseColor("#F5F5DC")
            "blanco" ->Color.WHITE
            "incoloro" ->Color.GRAY
            // Agrega más casos según los colores disponibles
            else -> getRandomColor()
        }
    }


    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    private fun getAllProductosPedidosAgrupados(listaProductosPedidos: MutableList<ProductoPedido>): MutableList<ProductoPedido> {
        val mapaProductosPedidos = mutableMapOf<String, Int>()
        for (pPedido in listaProductosPedidos) {
            val nombre = pPedido.nombre!!
            val metros = pPedido.metros
            if (mapaProductosPedidos.containsKey(nombre)) {
                mapaProductosPedidos[nombre] = mapaProductosPedidos[nombre]!! + metros
            } else {
                mapaProductosPedidos[nombre] = metros
            }
        }
        val listaProductosPedidosAgrupados = mutableListOf<ProductoPedido>()
        for ((nombre, metros) in mapaProductosPedidos) {
            val productoPedido = ProductoPedido(nombre, metros)
            listaProductosPedidosAgrupados.add(productoPedido)
        }
        return listaProductosPedidosAgrupados
    }

    private fun transformToChart(entries: MutableList<BarEntry>, data: Any) {
        entries.clear()
        when (data) {
            is String -> {
                mutableCloths.forEachIndexed { index, cloth ->
                    val value = when (data) {
                        "stock" -> cloth.stockActual?.toFloat() ?: 0f
                        "price" -> cloth.price?.toFloat() ?: 0f
                        "size" -> cloth.width?.times(cloth.long?.toFloat() ?: 0f) ?: 0f
                        else -> 0f
                    }
                    val entry = BarEntry((index + 1).toFloat(), value)
                    entries.add(entry)
                }
            }
            is List<*> -> {
                entries.clear()
                data.forEachIndexed { index, item ->
                    when (item) {
                        is Pair<*, *> -> {
                            val entry = BarEntry((index + 1).toFloat(), item.second.toString().toFloat())
                            entries.add(entry)
                        }
                        is ProductoPedido -> {
                            val value = item.metros.toFloat()
                            val entry = BarEntry((index + 1).toFloat(), value)
                            entries.add(entry)
                        }
                    }
                }
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        val index = e?.x?.toInt() ?: 0
        val value = e?.y ?: 0f
        val cloth = mutableCloths.getOrNull(index - 1)
        val order = mutableProductoPedido.getOrNull(index-1)
        val client = mutableClient.getOrNull(index-1)
        val name = cloth?.name ?: ""
        val clientName = client
        val orderName = order?.nombre
        val message = if (isClothReport() && isColorNeeded()) {
            val color = cloth?.color ?: ""
            "Color: $color - Cantidad del color: $value"
        }
        else if (isClothReport()){
            "$name: $value"
        }
        else if(isClientReport()) {
            "$clientName: $value"
        }
        else {
            "$orderName: $value"
        }
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
        Handler().postDelayed({ toast.cancel() }, 1000)
    }

    private fun isClientReport(): Boolean {
        val args = DetailReportFragmentArgs.fromBundle(requireArguments()).report.name
        return args == "Clientes con más pedidos"
    }

    private fun isClothReport(): Boolean {
        val args = DetailReportFragmentArgs.fromBundle(requireArguments()).report.name
        return args == "Telas por stock" || args == "Telas por precio" || args == "Telas por color" || args == "Telas por tamaño"
    }

    private fun isColorNeeded(): Boolean {
        val args = DetailReportFragmentArgs.fromBundle(requireArguments()).report.name
        return args == "Telas por color"
    }


    override fun onNothingSelected() {
        // No hacer nada
    }
}

