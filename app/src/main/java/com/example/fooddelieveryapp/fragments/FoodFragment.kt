package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.FoodAdapter
import com.example.fooddelieveryapp.databinding.FragmentFoodBinding
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.models.FoodModel

class FoodFragment : Fragment() {
    lateinit var binding: FragmentFoodBinding
    private lateinit var foodAdpater : FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFoodBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = GridLayoutManager(activity,2)
        foodAdpater = FoodAdapter(loadData(),activity as Context)
        binding.recyclerView.adapter = foodAdpater

        val verticalDividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        val horizontalDividerItemDecoration = DividerItemDecoration(activity, RecyclerView.HORIZONTAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> verticalDividerItemDecoration.setDrawable(drawable)
            }
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_horizontal, null)
            ?.let { drawable ->  horizontalDividerItemDecoration.setDrawable(drawable)
            }
        binding.recyclerView.addItemDecoration(verticalDividerItemDecoration)
        binding.recyclerView.addItemDecoration(horizontalDividerItemDecoration)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProvider(requireActivity())[RestauModel::class.java]
        val restaurant = vm.restau
        binding.restaurantName.text = restaurant?.name
        foodAdpater.onItemClick={
            val vm = ViewModelProvider(requireActivity())[FoodModel::class.java]
            vm.food = it
            vm.restaurant = restaurant
            findNavController().navigate(R.id.action_foodFragment_to_foodDetailsFragment)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadData() : List<Food>{
        val data  = mutableListOf<Food>()
        data.add(Food(R.drawable.restau,"loubya",240.0,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit,"))
        data.add(Food(R.drawable.restau,"loubya",240.0,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit,"))
        data.add(Food(R.drawable.restau,"loubya",240.0,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit,"))
        data.add(Food(R.drawable.restau,"loubya",240.0,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.Lorem ipsum dolor sit amet, consectetur adipiscing elit,"))
        return data

    }
}