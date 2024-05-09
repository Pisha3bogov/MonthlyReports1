package com.example.monthlyreport.ui.report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.monthlyreport.databinding.ReportListDetailedBinding
import com.example.monthlyreport.db.MainDb
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RepList: BottomSheetDialogFragment() {

    private var _binding: ReportListDetailedBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ReportListDetailedBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        //delItem(context)

    }

    /*fun delItem(context: Context) {

        val db = MainDb.getDb(context)

        binding.deleteBut.setOnClickListener {

            val id_report = binding.idReport.text.toString().toInt()

            val report = db.getReportDao().getRepId(id_report)

            db.getReportDao().deleteReport(report)
        }
    }*/
}