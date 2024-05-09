package com.example.monthlyreport.ui.product

import android.R.attr.key
import android.R.attr.value
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.monthlyreport.R
import com.example.monthlyreport.databinding.FragmentProductBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import kotlinx.coroutines.Dispatchers
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

        val addProdFrag = AddProductFragment()

        val setProdFrag = SetProductFragment()


        initTab(context)

        binding.openAddProductFragment.setOnClickListener {
            addProdFrag.show(parentFragmentManager,"Open add product fragment")
        }

        binding.update.setOnClickListener {
            initTab(context)
        }

        binding.listProd.setOnItemClickListener { parent, view, position, id ->

            val textViewName = parent.adapter.getView(position,view,parent).findViewById<TextView>(R.id.productName)

            setProdFrag.show(parentFragmentManager,textViewName.text.toString())

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initTab(context: Context) = runBlocking {

        val handler = Handler()

        launch(Dispatchers.IO) {
            val db = MainDb.getDb(context)

            val prodArr: List<Product> = db.getProductDao().getAllProduct()

            val arrayAdapter: ArrayAdapter<Product> = ProductArrayAdapter(context,prodArr)

            handler.post {
                binding.listProd.adapter = arrayAdapter

            }


        }

    }

}