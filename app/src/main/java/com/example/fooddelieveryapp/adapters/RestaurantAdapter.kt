package com.example.fooddelieveryapp.adapters
import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.utils.Util
import com.example.fooddelieveryapp.databinding.RestaurantLayoutBinding
import com.example.fooddelieveryapp.models.Restaurant
import com.example.fooddelieveryapp.utils.API_URL


class RestaurantAdapter(val data:List<Restaurant>, val context : Context):RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    private val util : Util = Util()
    var onItemClick : ((Restaurant)->Unit)? = null
    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = data[position]

        holder.binding.apply {
            name.text = restaurant.name
            Glide.with(context)
                .load(API_URL +restaurant.picture)
                .into(restauImage)
            rating.rating = restaurant.rating
            cuisine.text = restaurant.restaurantType
            numViews.text = "(${restaurant.reviews})"
            Adresse.text=restaurant.address
        }
        holder.binding.facebook.setOnClickListener{
            try{
                util.openPage(context, "fb://"+restaurant.facebook)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, "https://"+restaurant.facebook)
            }
        }
        holder.binding.instagram.setOnClickListener{
            try{
                util.openPage(context, "ig://"+restaurant.instagram)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, "https://"+restaurant.instagram)
            }
        }
        holder.binding.location.setOnClickListener{
            try{
                util.openPage(context, restaurant.location)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, restaurant.location)
            }
        }
        holder.binding.phone.setOnClickListener{
            try{
                util.dial(context, restaurant.phoneNumber)
            }catch(e : ActivityNotFoundException){
                print("your phone is Fed up")
            }
        }
        holder.binding.emailImage.setOnClickListener{
            try{
                util.email(context, restaurant.email)
            }catch(e : ActivityNotFoundException){
                print("you don't have mail app")
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(restaurant)
        }
    }



    class MyViewHolder(val binding: RestaurantLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



