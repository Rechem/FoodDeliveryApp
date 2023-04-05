package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.RestaurantAdapter
import com.example.fooddelieveryapp.databinding.ActivityRestaurantsListBinding
import com.example.fooddelieveryapp.models.Restaurant

class RestaurantsList : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantsListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRestaurantsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RestaurantAdapter(loadData(),this)
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    fun loadData():List<Restaurant> {
        val data = mutableListOf<Restaurant>()
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey", 4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/"
            ,"24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        data.add(
            Restaurant("Restau1", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
            "Turkey",4.5F,11,"0534523142","restau1@gmail.com",
            "fb://page/218641444910278","https://www.facebook.com/RenaultRomania/photos/",
            "https://www.instagram.com/noufel_17/","https://www.instagram.com/noufel_17/",
                "24 Rue Didouche Mourad")
        )
        return data
    }
}