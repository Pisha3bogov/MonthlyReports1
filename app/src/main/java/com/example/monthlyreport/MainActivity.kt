package com.example.monthlyreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.monthlyreport.databinding.ActivityMainBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var db = MainDb.getDb(this)

        //Database close, не открывается бд нужно решить
        
        Thread {
            db.getProductDao().insertProduct(Product(null,"fds",24))
            db.getProductDao().insertProduct(Product(null,"gdfs",24))

            val nameProduct: List<String> = db.getProductDao().getNameProduct()

            val spinner: Spinner = findViewById(R.id.spinnerProduct)

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameProduct)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

        }

        /*binding.addUserProduct.setOnClickListener{
            val product = Product(null, binding.spinnerProduct.getItemAtPosition(this), binding.enterQuantity.text.toString().toInt() )

            Thread {
                db.getProductDao().insertProduct(product)
            }
        }*/
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

