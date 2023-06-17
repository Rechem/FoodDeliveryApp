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
        binding= ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.itemPriceList.adapter = ItemPriceAdapter(loadData(), this)

        binding.totalSum.text = "4200 DZD"
    }

    fun loadData():List<CartItemModal> {
        val data = mutableListOf<CartItemModal>()
        return data
    }
}