package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.RestaurantAdapter

import com.example.fooddelieveryapp.databinding.FragmentRestaurantsBinding
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.models.Restaurant


class RestaurantsFragment : Fragment() {
    lateinit var binding: FragmentRestaurantsBinding
    lateinit var restaurantAdapter : RestaurantAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        restaurantAdapter.onItemClick = {
            // replace intent with fragment navigation
            val vm = ViewModelProvider(requireActivity())[RestauModel::class.java]
            vm.restau = it
            findNavController().navigate(R.id.action_restaurantsFragment_to_foodFragment)
//            val intent = Intent(this, FoodList::class.java)
//            intent.putExtra("restaurant",it )
//            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRestaurantsBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        restaurantAdapter = RestaurantAdapter(loadData(),activity as Context)
        binding.recyclerView.adapter = restaurantAdapter


        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        return binding.root

    }


    fun loadData():List<Restaurant> {
        val data = mutableListOf<Restaurant>()
        data.add(
            Restaurant("dezdouz", R.drawable.restau,"https://goo.gl/maps/Wj9NRgtKmX3h8jNE7",
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