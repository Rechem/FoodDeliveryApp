package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.CartItemAdapter
import com.example.fooddelieveryapp.databinding.ActivityCartBinding
import com.example.fooddelieveryapp.models.CartItem

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = CartItemAdapter(loadData(), this)

        binding.mealsPrice.text = "69 DZD"
        binding.deliveryFeesPrice.text = "69 DZD"

        binding.totalCart.text = "4200 DZD"
    }

    fun loadData():List<CartItem> {
        val data = mutableListOf<CartItem>()
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))

        return data
    }
}