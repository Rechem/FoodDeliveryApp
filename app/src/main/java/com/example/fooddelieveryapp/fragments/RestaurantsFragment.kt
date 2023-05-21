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
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.RestaurantAdapter

import com.example.fooddelieveryapp.databinding.FragmentRestaurantsBinding
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.models.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RestaurantsFragment : Fragment() {
    lateinit var binding: FragmentRestaurantsBinding
    lateinit var restaurantAdapter : RestaurantAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding= FragmentRestaurantsBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE
        loadData()
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)


    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = Endpoint.createEndpoint().getRestaurants()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val restaurants = response.body()
                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                    restaurantAdapter = RestaurantAdapter(restaurants!!,activity as Context)
                    binding.recyclerView.adapter = restaurantAdapter
                    restaurantAdapter.onItemClick = {
                        val vm = ViewModelProvider(requireActivity())[RestauModel::class.java]
                        vm.restau = it
                        findNavController().navigate(R.id.action_restaurantsFragment_to_foodFragment)
                    }
                } else {
                    throw Exception("failed to load restaurants, error code : ${response.code()}")
                }
            }
        }
    }
}