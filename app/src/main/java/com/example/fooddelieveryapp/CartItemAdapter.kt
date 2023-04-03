package com.example.fooddelieveryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.databinding.CartItemLayoutBinding


class CartItemAdapter(val data:List<CartItem>, val ctx: Context):RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            image.setImageResource(data[position].image)
            name.text = data[position].name
            price.text = data[position].price.toString() + " DZD"
            quantity.text = data[position].quantity.toString()
            addBtn.setOnClickListener{

            }
            minusBtn.setOnClickListener{

            }
        }
    }


    class MyViewHolder(val binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



