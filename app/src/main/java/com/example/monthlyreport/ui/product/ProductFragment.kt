package com.example.monthlyreport.ui.product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.example.monthlyreport.R
import com.example.monthlyreport.databinding.FragmentProductBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ProductFragment : Fragment() {

    private var _binding : FragmentProductBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        initTab(context)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initTab(context: Context) = runBlocking {

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val prodArr: List<Product> = db.getProductDao().getAllProduct()

            val arrayAdapter: ArrayAdapter<Product> = ProductArrayAdapter(context,prodArr)

            binding.listProd.adapter = arrayAdapter
        }

    }

}