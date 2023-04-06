package com.example.fooddelieveryapp.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.databinding.FoodLayoutBinding
import com.example.fooddelieveryapp.models.Food


class FoodAdapter(val data:List<Food>, val context: Context):RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {
    var onItemClick : ((Food)->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FoodLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food = data[position]
        holder.binding.apply {
            name.text = food.name
            foodPrice.text = "${food.price} DA"
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(food)
        }

    }



    class MyViewHolder(val binding: FoodLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}