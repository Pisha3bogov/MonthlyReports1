package com.example.monthlyreport

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.monthlyreport.databinding.ActivityMainBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import java.util.Calendar
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextSpinner()


        binding.addUserProduct.setOnClickListener {

            addReport()
            Thread.sleep(500)
            delSelectRep()

        }


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addReport () {

        val db = MainDb.getDb(this)

        val c = Calendar.getInstance()


        GlobalScope.async {
            try {

                val product: Product =
                    db.getProductDao()
                        .searchByName(binding.spinnerProduct.selectedItem.toString())

                val report = Report(
                    null,
                    c.get(Calendar.DATE).toInt(),
                    c.get(Calendar.MONTH).toInt(),
                    c.get(Calendar.YEAR).toInt(),
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
        if (binding.errorTextView.text == "Продукт добавлен") {
            binding.spinnerProduct.setSelection(0)
            binding.enterQuantity.setText("")
        }
    }


    private fun setTextSpinner() {

        val db = MainDb.getDb(this)

        val nameProd: ArrayList<String> = arrayListOf("Выберите продукт")

        db.getProductDao().getAllProduct().asLiveData().observe(this) {
            it.forEach {
                nameProd.add(it.name)
            }
        }


        val spinner: Spinner = findViewById(R.id.spinnerProduct)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameProd)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

