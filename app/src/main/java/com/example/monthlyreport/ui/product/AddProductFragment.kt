package com.example.monthlyreport.ui.product

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monthlyreport.R
import com.example.monthlyreport.databinding.AddProductBinding
import com.example.monthlyreport.databinding.FragmentHomeBinding
import com.example.monthlyreport.databinding.FragmentProductBinding
import com.example.monthlyreport.db.MainDb
import com.example.monthlyreport.db.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding : AddProductBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddProductBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        binding.addProduct.setOnClickListener {
            addProdDb(context)
        }



    }


    fun addProdDb(context: Context) = runBlocking {

        val db = MainDb.getDb(context)

        val handler = Handler()

        async(Dispatchers.IO) {

            try {

                if (binding.nameEditText.text.toString() != "") {

                    if(db.getProductDao().searchByName(binding.nameEditText.text.toString()) == null) {
                        val product = Product(
                            null,
                            binding.nameEditText.text.toString(),
                            binding.priceEditText.text.toString().toInt()
                        )

                        db.getProductDao().insertProduct(product)

                        parentFragmentManager.beginTransaction().remove(this@AddProductFragment)
                            .commit()

                        binding.nameEditText.text.clear()
                        binding.priceEditText.text.clear()

                        handler.post {
                            Toast.makeText(context, "Продукт добавлен", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        binding.errorTextView.setText("Продукт уже существует")
                        binding.errorTextView.setTextColor(Color.RED)
                    }


                }else {
                    binding.errorTextView.setText("Введите название")
                    binding.errorTextView.setTextColor(Color.RED)
                }

            }catch (e:NumberFormatException){
                binding.errorTextView.setText("Введите количество")
                binding.errorTextView.setTextColor(Color.RED)
            }

        }

    }
}