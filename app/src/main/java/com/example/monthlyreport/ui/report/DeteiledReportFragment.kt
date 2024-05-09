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
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.monthlyreport.R
import com.example.monthlyreport.databinding.DeteiledReportFragmentBinding
import com.example.monthlyreport.databinding.ReportListDetailedBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Report
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Month
import java.util.Calendar

class DeteiledReportFragment : BottomSheetDialogFragment() {

    private val dataModel: DataModel by activityViewModels()

    private var _binding: DeteiledReportFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DeteiledReportFragmentBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        val month = dataModel.month.value.toString().toInt()

        val year = dataModel.year.value.toString().toInt()

        initTab(context,month, year)

    }

    private fun initTab(context: Context, month: Int, year: Int) = runBlocking {

        val handler = Handler()

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val name = dataModel.name.value.toString().drop(10)

            val idProd = db.getProductDao().searchByName( name)

            val repArr: List<Report> = db.getReportDao().getRepIdAndMonth(idProd.id!!, month, year)

            val arrayAdapter: ArrayAdapter<Report> = ReportDeteilAdapter(context, repArr)

            handler.post {
                binding.listReport.adapter = arrayAdapter
            }

        }

    }

}