package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelieveryapp.databinding.ActivityDeliveryCheckoutBinding

class DeliveryCheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeliveryCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDeliveryCheckoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}