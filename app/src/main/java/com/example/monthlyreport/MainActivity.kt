package com.example.monthlyreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monthlyreport.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBtv = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBtv.setupWithNavController(findNavController(R.id.fragmentContainerView3))


    }

}

