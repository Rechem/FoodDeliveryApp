package com.example.fooddelieveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.models.CartItem
import com.example.fooddelieveryapp.databinding.CartItemLayoutBinding
import com.example.fooddelieveryapp.utils.API_URL


class CartItemAdapter(val data:List<CartItem>, val ctx: Context):RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(ctx)
                .load(API_URL +data[position].image)
                .into(image)

            name.text = data[position].name
            price.text = data[position].price.toString() + " DZD"
            quantity.text = data[position].quantity.toString()
        }
    }


    class MyViewHolder(val binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



