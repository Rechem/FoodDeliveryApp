package com.example.fooddelieveryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelieveryapp.adapters.ItemPriceAdapter
import com.example.fooddelieveryapp.databinding.ActivityOrderBinding
import com.example.fooddelieveryapp.models.CartItemModal

class OrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun loadData():List<CartItemModal> {
        val data = mutableListOf<CartItemModal>()
        return data
    }
}