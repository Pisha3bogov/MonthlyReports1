package com.example.monthlyreport.ui.product

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monthlyreport.databinding.SetProductBinding
import com.example.monthlyreport.db.MainDb
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SetProductFragment : BottomSheetDialogFragment() {

    private var _binding: SetProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SetProductBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view.context)

        binding.setProductPrice.setOnClickListener {
            setPrice(view.context)
        }

        binding.delProduct.setOnClickListener {
            delProd(view.context)
        }
    }

    fun init(context: Context) = runBlocking {

        val db = MainDb.getDb(context)

        launch(Dispatchers.IO) {

            val prod = db.getProductDao().searchByName(tag.toString())

            binding.nameEditText.text = prod.name

            binding.priceEditText.hint = prod.price.toString()
        }

    }

    fun setPrice(context: Context) = runBlocking {
        val db = MainDb.getDb(context)

        val handler = Handler()

        async(Dispatchers.IO) {

            try {

                val prod = db.getProductDao().searchByName(binding.nameEditText.text.toString())

                if (prod.price != binding.priceEditText.text.toString().toInt()) {

                    prod.price = binding.priceEditText.text.toString().toInt()

                    db.getProductDao().updateProduct(prod)

                    parentFragmentManager.beginTransaction().remove(this@SetProductFragment)
                        .commit()

                    handler.post {
                        Toast.makeText(context, "Цена изменена", Toast.LENGTH_LONG).show()
                    }

                } else {
                    handler.post {
                        Toast.makeText(context, "Цена совпадает с преждней", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            } catch (e: NumberFormatException) {
                handler.post {
                    Toast.makeText(context, "Введите цену", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    fun delProd(context: Context) = runBlocking {
        val db = MainDb.getDb(context)

        val handler = Handler()

        launch(Dispatchers.IO) {
            val prod = db.getProductDao().searchByName(binding.nameEditText.text.toString())

            db.getProductDao().deleteProduct(prod)

            parentFragmentManager.beginTransaction().remove(this@SetProductFragment).commit()
        }

        Toast.makeText(context, "Продукт удален", Toast.LENGTH_LONG).show()

    }
}