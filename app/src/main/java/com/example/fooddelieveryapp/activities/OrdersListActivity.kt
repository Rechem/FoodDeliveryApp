package com.example.fooddelieveryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.adapters.OrdersListAdapter
import com.example.fooddelieveryapp.databinding.ActivityOrdersListBinding
import com.example.fooddelieveryapp.models.OrderItem

class OrdersListActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrdersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrdersListBinding.inflate(layoutInflater)
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