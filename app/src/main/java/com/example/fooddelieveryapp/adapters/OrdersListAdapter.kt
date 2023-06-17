package com.example.fooddelieveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.databinding.OrderItemLayoutBinding
import com.example.fooddelieveryapp.models.OrderItem
import com.example.fooddelieveryapp.models.Restaurant

class OrdersListAdapter(val data:List<OrderItem>, val ctx: Context): RecyclerView.Adapter<OrdersListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(OrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    var onItemClick : ((OrderItem)->Unit)? = null
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val orderItem = data[position]
        holder.binding.apply {
            orderNumber.text = "Order #${orderItem.id}"
            orderDateCard.text = orderItem.date
            orderStatusCard.text = orderItem.status
            orderRestaurantCard.text = orderItem.restaurantName
            orderPriceCard.text = "${orderItem.totalPrice.toString()} DZD"
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(orderItem)
        }
    }


    class MyViewHolder(val binding: OrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}