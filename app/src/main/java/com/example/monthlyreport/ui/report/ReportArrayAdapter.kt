package com.example.monthlyreport.ui.report

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.monthlyreport.R
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReportArrayAdapter(private val context: Context, private val reports: List<Report>) :
    ArrayAdapter<Report>(context, R.layout.list_report, reports) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)

        val view: View = inflater.inflate(R.layout.list_report, null)

        val dateReport: TextView = view.findViewById(R.id.dateReport)

        val countReport: TextView = view.findViewById(R.id.countReport)

        val allPrice: TextView = view.findViewById(R.id.allPrice)

        val date: String = "Дата: " + reports[position].id_product.toString() + ". " +
                reports[position].month.toString() + ". " + reports[position].year.toString()

        initProdName(context, view, position)

        dateReport.text = date

        countReport.text = "Количество: " + reports[position].quantity.toString()

        allPrice.text = "Цена: " + reports[position].price.toString()

        return view
    }

    fun initProdName(context: Context, view: View, position: Int) = runBlocking {

        val db = MainDb.getDb(context)

        val prodName: TextView = view.findViewById(R.id.prodName)

        launch(Dispatchers.IO) {

            prodName.text = "Название: " + db.getProductDao()
                .searchById(reports[position].id_product).name
        }

    }
}
