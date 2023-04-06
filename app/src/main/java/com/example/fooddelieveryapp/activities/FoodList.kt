package com.example.fooddelieveryapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.FoodAdapter
import com.example.fooddelieveryapp.databinding.ActivityFoodListBinding
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.Restaurant

class FoodList : AppCompatActivity() {
    lateinit var binding: ActivityFoodListBinding
    var foodAdpater = FoodAdapter(loadData(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFoodListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = foodAdpater
        val restaurant = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("restaurant",Restaurant::class.java)
        }else{
            intent.getParcelableExtra<Restaurant>("restaurant")
        }
        binding.restaurantName.text = restaurant?.name
        binding.back.setOnClickListener {
            finish();
        }
        val verticalDividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        val horizontalDividerItemDecoration = DividerItemDecoration(this, RecyclerView.HORIZONTAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> verticalDividerItemDecoration.setDrawable(drawable)
            }
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_horizontal, null)
            ?.let { drawable ->  horizontalDividerItemDecoration.setDrawable(drawable)
            }
        binding.recyclerView.addItemDecoration(verticalDividerItemDecoration)
        binding.recyclerView.addItemDecoration(horizontalDividerItemDecoration)
    }
    private fun loadData() : List<Food>{
        val data  = mutableListOf<Food>()
        data.add(Food(R.drawable.restau,"loubya",240.0))
        data.add(Food(R.drawable.restau,"loubya",240.0))
        data.add(Food(R.drawable.restau,"loubya",240.0))
        data.add(Food(R.drawable.restau,"loubya",240.0))
        return data

    }

}