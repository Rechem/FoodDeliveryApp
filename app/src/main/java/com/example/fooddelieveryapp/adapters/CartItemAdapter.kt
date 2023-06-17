package com.example.fooddelieveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.models.CartItemModal
import com.example.fooddelieveryapp.databinding.CartItemLayoutBinding
import com.example.fooddelieveryapp.utils.API_URL
import com.example.fooddelieveryapp.viewmodels.CartViewModel


class CartItemAdapter(var data:MutableList<CartItemModal>, val ctx: Context, var cartViewModel: CartViewModel):RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(ctx)
                .load(API_URL +"/"+data[position].image)
                .into(image)

            name.text = data[position].name
            price.text = data[position].price.toString() + " DZD"
            quantityItem.text = data[position].quantity.toString()

            addBtnItem.setOnClickListener{

                cartViewModel.addToCartTotal(data[position].price)
                cartViewModel.incrementCartItemQuantity(data[position].id);
                data[position].quantity++;
                notifyItemChanged(position, "quantity")
            }

            minusBtnItem.setOnClickListener{
                if(data[position].quantity > 1) {
                    cartViewModel.subFromCartTotal(data[position].price)
                    cartViewModel.decrementCartItemQuantity(data[position].id);
                    data[position].quantity--;
                    notifyItemChanged(position, "quantity")
                }
            }

            removeCartItem.setOnClickListener{
                cartViewModel.deleteCartItem(data[position].id);
                cartViewModel.subFromCartTotal(data[position].price * data[position].quantity);
                data.removeAt(position);
                notifyItemRemoved(position);
            }
        }
    }

    fun clearItems(){
        data = ArrayList();
        notifyDataSetChanged();
    }


    class MyViewHolder(val binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



