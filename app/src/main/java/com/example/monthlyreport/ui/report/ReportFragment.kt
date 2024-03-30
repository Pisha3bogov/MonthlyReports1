package com.example.monthlyreport.ui.report

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var _binding : FragmentReportBinding? = null
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

        initTab(view.context, Calendar.MONTH + 1)

        initIncome(view.context)

        initAmount(view.context)

        initSpinMonth(view.context)

    }

    private fun initTab(context: Context, month: Int) = runBlocking {

        val handler = Handler()

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val repArr: List<Report> = db.getReportDao().getRepMoth(month)

            val arrayAdapter: ArrayAdapter<Report> = ReportArrayAdapter(context,repArr)

            handler.post {
                binding.listReport.adapter = arrayAdapter

            }


        }

    }

    private fun initSpinMonth(context: Context) = runBlocking {
        val map = listOf(
            "Текущий месяц",
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        )

        val spinner: Spinner = binding.monthSpin

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, map)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

    }

    private fun  initIncome(context: Context) = runBlocking {
        val db = MainDb.getDb(context)

        val handler = Handler()

        launch(Dispatchers.IO) {
            var income = 0

            db.getReportDao().getAllReport().forEach {
                income += it.price
            }

            handler.post{
                binding.income.text = "Общая сумма = " + income.toString()
            }
        }
    }

    private fun initAmount(context: Context) = runBlocking {
        val db = MainDb.getDb(context)

        val handler = Handler()

        launch(Dispatchers.IO) {
            var amount = 0

            db.getReportDao().getAllReport().forEach {
                amount += it.quantity
            }

            handler.post{
                binding.amount.text = "Количество = " + amount.toString()
            }
        }
    }
}