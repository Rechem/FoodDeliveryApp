package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelieveryapp.databinding.ActivityDrawerBinding

class DrawerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDrawerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}