package com.example.fooddelieveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.databinding.OrderItemLayoutBinding
import com.example.fooddelieveryapp.models.OrderItem

class OrdersListAdapter(val data:List<OrderItem>, val ctx: Context): RecyclerView.Adapter<OrdersListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(OrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            orderIdCard.text = data[position].id
            orderDateCard.text = data[position].date
            orderStatusCard.text = data[position].status
            orderRestaurantCard.text = data[position].restaurantName
            orderPriceCard.text = data[position].totalPrice.toString()
        }
    }


    class MyViewHolder(val binding: OrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}