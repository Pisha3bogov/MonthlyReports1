package com.example.monthlyreport.ui.report

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.monthlyreport.databinding.FragmentHomeBinding
import com.example.monthlyreport.databinding.FragmentReportBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import com.example.monthlyreport.ui.product.ProductArrayAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar


class ReportFragment : Fragment() {

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

        initSpinMonth(context)

        initSpinYear(context)

        initTab(context, Calendar.MONTH + 1)

//        initIncome(view.context)
//
//        initAmount(view.context)

        initSpinMonth(context)

        binding.monthSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                initTab(context, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    private fun initTab(context: Context, month: Int) = runBlocking {

        val handler = Handler()

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val repArr: List<Report> = db.getReportDao().getRepMoth(month)

            val arrayAdapter: ArrayAdapter<Report> = ReportArrayAdapter(context, repArr)

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

    private fun initSpinMonth(context: Context) = runBlocking {
        val map = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

        val spinner: Spinner = binding.monthSpin

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, map)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.setSelection(Calendar.MONTH + 1)

    }

    private fun initSpinYear(context: Context) = runBlocking {
        val arr = listOf(2024,2025,2026,2027)

        val spinner: Spinner = binding.yearSpin

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arr)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        //spinner.setSelection(Calendar.YEAR)

    }

}