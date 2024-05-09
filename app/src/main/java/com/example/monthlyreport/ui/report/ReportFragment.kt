package com.example.monthlyreport.ui.report

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.monthlyreport.R
import com.example.monthlyreport.databinding.FragmentHomeBinding
import com.example.monthlyreport.databinding.FragmentReportBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import com.example.monthlyreport.ui.product.ProductArrayAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar


class ReportFragment : Fragment() {

    private val dataModel: DataModel by activityViewModels()

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReportBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        val calendar = Calendar.getInstance()

        val detRepFrag = DeteiledReportFragment()

        val spinnerYear: Spinner = binding.yearSpin

        val spinnerMonth: Spinner = binding.monthSpin

        initSpinMonth(context, calendar, spinnerMonth)

        initSpinYear(context, calendar, spinnerYear)

        initTab(context, spinnerMonth.selectedItem.toString().toInt(),
            spinnerYear.selectedItem.toString().toInt())

        dataModel.month.value = spinnerMonth.selectedItem.toString().toInt()

        dataModel.year.value = spinnerYear.selectedItem.toString().toInt()

        binding.monthSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                initTab(context, spinnerMonth.selectedItem.toString().toInt(),
                    spinnerYear.selectedItem.toString().toInt())

                dataModel.month.value = spinnerMonth.selectedItem.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.yearSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                initTab(context, spinnerMonth.selectedItem.toString().toInt(),
                    spinnerYear.selectedItem.toString().toInt())

                dataModel.year.value = spinnerYear.selectedItem.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.listReport.setOnItemClickListener { parent, view, position, id ->

            dataModel.name.value = view.findViewById<TextView>(R.id.prodName).text.toString()

            val textViewName = parent.adapter.getView(position,view,parent).
            findViewById<TextView>(R.id.prodName)

            detRepFrag.show(parentFragmentManager,textViewName.text.toString())

        }

    }

    private fun initTab(context: Context, month: Int, year: Int) = runBlocking {

        val handler = Handler()

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val prodList = db.getProductDao().getAllProduct()

            val reportDateObj = prodList.map { product ->
                ReportDateObj(product.id, product.name , null , null,null)
            }

            reportDateObj.forEach {reportDateObj ->

                val listIdRep = mutableListOf<Int>()

                var quantity = 0

                var price = 0

                db.getReportDao().getRepIdAndMonth(reportDateObj.id!!,month, year).forEach{
                    listIdRep.add(it.id!!)
                    quantity += it.quantity
                    price += it.price
                }

                reportDateObj.id_report = listIdRep

                reportDateObj.quantity = quantity

                reportDateObj.price = price
            }

            val sortRepDateObj = reportDateObj.sortedBy { it.quantity }.reversed()


            val arrayAdapter: ArrayAdapter<ReportDateObj> = ReportArrayAdapter(context, sortRepDateObj)

            var income = 0

            var amount = 0

            db.getReportDao().getRepMoth(month).forEach {
                income += it.price
            }

            db.getReportDao().getRepMoth(month).forEach {
                amount += it.quantity
            }

            handler.post {
                binding.listReport.adapter = arrayAdapter

                binding.income.text = "Общая сумма = " + income.toString()

                binding.amount.text = "Количество = " + amount.toString()
            }


        }

    }

    private fun initSpinMonth(context: Context, calendar: Calendar, spinner: Spinner) = runBlocking {

        val map = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, map)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        val currentMonth = map.indexOfFirst { it == calendar.get(Calendar.MONTH) + 1 }

        spinner.setSelection(currentMonth)

    }

    private fun initSpinYear(context: Context, calendar: Calendar, spinner :Spinner) = runBlocking {
        val arr = listOf(2023, 2024, 2025, 2026, 2027)

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arr)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        val currentYear = arr.indexOfFirst {it == calendar.get(Calendar.YEAR)}

        spinner.setSelection(currentYear)

    }
}