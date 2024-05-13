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
import com.example.monthlyreport.databinding.FragmentHomeBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            Thread.sleep(1000)
            delSelectRep()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun addReport (context: Context) = runBlocking {

        val db = MainDb.getDb(context)

        val c = Calendar.getInstance()


        async(Dispatchers.IO) {
            try {

                val product: Product =
                    db.getProductDao()
                        .searchByName(binding.spinnerProduct.selectedItem.toString())

                val report = Report(
                    null,
                    c.get(Calendar.DAY_OF_MONTH),
                    c.get(Calendar.MONTH) + 1,
                    c.get(Calendar.YEAR),
                    product.id!!,
                    binding.enterQuantity.text.toString().toInt(),
                    product.price * binding.enterQuantity.text.toString().toInt()
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
        if (binding.errorTextView.text == "Продукт добавлен") {
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