package com.example.fooddelieveryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fooddelieveryapp.databinding.CartItemLayoutBinding
import com.example.fooddelieveryapp.databinding.ItemPriceLayoutBinding

class ItemPriceAdapter(val data:List<CartItem>, val ctx: Context): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): CartItem {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item:CartItem = getItem(position);
//        ItemPriceLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemPriceLayoutBinding

        val binding: ItemPriceLayoutBinding
        if (convertView == null) {
            binding = ItemPriceLayoutBinding.inflate(LayoutInflater.from(ctx), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemPriceLayoutBinding
        }

        // Set the text of the list item
        binding.nameItem.text = item.name;
        binding.quantityItem.text = item.quantity.toString()
        binding.priceItem.text = (item.price * item.quantity).toString() + " DZD"

        return binding.root
    }


}