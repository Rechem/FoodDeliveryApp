package com.example.fooddelieveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.databinding.OrdersListActivityBinding

class OrdersListActivity : AppCompatActivity() {

    lateinit var binding: OrdersListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= OrdersListActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ordersRecyclerView.adapter = OrdersListAdapter(loadData(), this)

    }

    fun loadData():List<OrderItem> {
        val data = mutableListOf<OrderItem>()
        data.add(OrderItem("245", "Ambrosial", "4/3/2023", 2400, "In progress"))
        data.add(OrderItem("245", "Ambrosial", "4/3/2023", 2400, "In progress"))
        data.add(OrderItem("245", "Ambrosial", "4/3/2023", 2400, "In progress"))
        data.add(OrderItem("245", "Ambrosial", "4/3/2023", 2400, "In progress"))


        return data
    }
}