package com.example.fooddelieveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.databinding.OrderActivityBinding

class OrderActivity : AppCompatActivity() {

    lateinit var binding: OrderActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= OrderActivityBinding.inflate(layoutInflater)
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