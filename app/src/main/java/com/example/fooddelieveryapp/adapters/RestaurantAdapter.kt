package com.example.fooddelieveryapp.adapters

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.utils.Util
import com.example.fooddelieveryapp.databinding.RestaurantLayoutBinding
import com.example.fooddelieveryapp.models.Restaurant


class RestaurantAdapter(val data:List<Restaurant>, val context: Context):RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    val util : Util = Util()
    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            name.text = data[position].name
            restauImage.setImageResource(data[position].logo)
            rating.rating = data[position].rating
            cuisine.text = data[position].cuisineType
            numViews.text = "(${data[position].reviews})"
            Adresse.text=data[position].adresse
        }
        holder.binding.facebook.setOnClickListener{
            try{
                util.openPage(context, data[position].fbMob)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, data[position].fbWeb)
            }
        }
        holder.binding.instagram.setOnClickListener{
            try{
                util.openPage(context, data[position].igMob)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, data[position].igWeb)
            }
        }
        holder.binding.location.setOnClickListener{
            try{
                util.openPage(context, data[position].location)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, data[position].location)
            }
        }
        holder.binding.phone.setOnClickListener{
            try{
                util.dial(context, data[position].phone)
            }catch(e : ActivityNotFoundException){
                print("your phone is Fed up")
            }
        }
        holder.binding.emailImage.setOnClickListener{
            try{
                util.email(context, data[position].email)
            }catch(e : ActivityNotFoundException){
                print("you don't have mail app you prick")
            }
        }
    }



    class MyViewHolder(val binding: RestaurantLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



