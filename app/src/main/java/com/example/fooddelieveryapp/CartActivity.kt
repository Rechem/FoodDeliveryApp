package com.example.fooddelieveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.databinding.CartActivityBinding

class CartActivity : AppCompatActivity() {

    lateinit var binding: CartActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= CartActivityBinding.inflate(layoutInflater)
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