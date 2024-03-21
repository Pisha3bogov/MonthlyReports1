package com.example.monthlyreport.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.monthlyreport.databinding.FragmentHomeBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        setTextSpinner(context)

        binding.addUserProduct.setOnClickListener {

            addReport(context)
            Thread.sleep(500)
            delSelectRep()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun addReport (context: Context) {

        val db = MainDb.getDb(context)

        val c = Calendar.getInstance()


        GlobalScope.async {
            try {

                val product: Product =
                    db.getProductDao()
                        .searchByName(binding.spinnerProduct.selectedItem.toString())

                val report = Report(
                    null,
                    c.get(Calendar.DATE),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.YEAR),
                    product.id!!,
                    binding.enterQuantity.text.toString().toInt(),
                    product.price
                )

                db.getReportDao().insertReport(report)

                binding.errorTextView.setText("Продукт добавлен")
                binding.errorTextView.setTextColor(Color.GREEN)


                true


            } catch (e: NullPointerException) {
                binding.errorTextView.setText("Выберите продукт")
                binding.errorTextView.setTextColor(Color.RED)
                false
            } catch (e: NumberFormatException) {
                binding.errorTextView.setText("Введите количество")
                binding.errorTextView.setTextColor(Color.RED)
                false
            }

        }

    }

    private fun delSelectRep() {
        if (binding.errorTextView.text == "Продукт добавлен (30)") {
            binding.spinnerProduct.setSelection(0)
            binding.enterQuantity.setText("")
        }

    }
    private fun setTextSpinner(context: Context) = runBlocking {

        val db = MainDb.getDb(context)

        val nameProd: ArrayList<String> = arrayListOf("Выберите продукт")

         launch(Dispatchers.IO) {

            db.getProductDao().getAllProduct().forEach {
                    nameProd.add(it.name)
            }
        }


        val spinner: Spinner = binding.spinnerProduct

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, nameProd)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
    }
}