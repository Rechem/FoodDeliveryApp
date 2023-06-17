package com.example.fooddelieveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.models.CartItemModal
import com.example.fooddelieveryapp.databinding.ItemPriceLayoutBinding
import com.example.fooddelieveryapp.databinding.RestaurantLayoutBinding
import com.example.fooddelieveryapp.models.OrderMeal
import com.example.fooddelieveryapp.utils.API_URL

class ItemPriceAdapter(val data:List<OrderMeal>, val ctx: Context): RecyclerView.Adapter<ItemPriceAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPriceAdapter.MyViewHolder {
        return MyViewHolder(
            ItemPriceLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
//        ItemPriceLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemPriceLayoutBinding

        holder.binding.apply {
            nameItem.text = item.name;
            quantityItem.text = item.quantity.toString()
            priceItem.text = "${(item.price * item.quantity).toString()} DA"
        }
    }

    class MyViewHolder(val binding: ItemPriceLayoutBinding) : RecyclerView.ViewHolder(binding.root)


}