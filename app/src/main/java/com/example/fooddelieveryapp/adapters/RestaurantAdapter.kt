package com.example.fooddelieveryapp.adapters
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
    private val util : Util = Util()
    var onItemClick : ((Restaurant)->Unit)? = null
    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = data[position]

        holder.binding.apply {
            name.text = restaurant.name
            restauImage.setImageResource(data[position].logo)
            rating.rating = restaurant.rating
            cuisine.text = restaurant.cuisineType
            numViews.text = "(${restaurant.reviews})"
            Adresse.text=restaurant.adresse
        }
        holder.binding.facebook.setOnClickListener{
            try{
                util.openPage(context, restaurant.fbMob)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, restaurant.fbWeb)
            }
        }
        holder.binding.instagram.setOnClickListener{
            try{
                util.openPage(context, restaurant.igMob)
            }catch(e : ActivityNotFoundException){
                util.openPage(context, restaurant.igWeb)
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
                util.dial(context, restaurant.phone)
            }catch(e : ActivityNotFoundException){
                print("your phone is Fed up")
            }
        }
        holder.binding.emailImage.setOnClickListener{
            try{
                util.email(context, restaurant.email)
            }catch(e : ActivityNotFoundException){
                print("you don't have mail app you prick")
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(restaurant)
        }
    }



    class MyViewHolder(val binding: RestaurantLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



