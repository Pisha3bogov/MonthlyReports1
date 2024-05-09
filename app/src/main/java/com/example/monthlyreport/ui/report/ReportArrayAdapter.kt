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

class ReportArrayAdapter(private val context: Context, private val reports: List<ReportDateObj>) : //Был Report
    ArrayAdapter<ReportDateObj>(context, R.layout.list_report, reports) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)

        val view: View = inflater.inflate(R.layout.list_report, null)


        val countReport: TextView = view.findViewById(R.id.countReport)

        val allPrice: TextView = view.findViewById(R.id.allPrice)

        val prodName: TextView = view.findViewById(R.id.prodName)

        prodName.text = "Название: " + reports[position].name

        countReport.text = "Количество: " + reports[position].quantity.toString()

        allPrice.text = "Цена: " + reports[position].price.toString()

        return view
    }
}
