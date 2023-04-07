package com.example.fooddelieveryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.ItemPriceAdapter
import com.example.fooddelieveryapp.databinding.ActivityOrderBinding
import com.example.fooddelieveryapp.models.CartItem

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

    fun loadData():List<CartItem> {
        val data = mutableListOf<CartItem>()
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))

        return data
    }
}