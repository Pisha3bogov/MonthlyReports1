package com.example.monthlyreport.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.monthlyreport.R
import com.example.monthlyreport.db.Product

class ProductArrayAdapter(private val context: Context,private val products: List<Product>)
: ArrayAdapter<Product>(context, R.layout.list_product, products) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)

        val view: View = inflater.inflate(R.layout.list_product, null)

        val prodName: TextView = view.findViewById(R.id.productName)

        val prodPrice: TextView = view.findViewById(R.id.productPrice)

        prodName.text = products[position].name

        prodPrice.text = products[position].price.toString()

        return view
    }

}