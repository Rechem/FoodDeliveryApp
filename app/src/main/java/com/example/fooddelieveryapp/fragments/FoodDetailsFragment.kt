package com.example.fooddelieveryapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentFoodDetailsBinding
import com.example.fooddelieveryapp.models.FoodModel
import com.example.fooddelieveryapp.models.RestauModel

class FoodDetailsFragment : Fragment() {
    lateinit var binding: FragmentFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFoodDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProvider(requireActivity())[FoodModel::class.java]
        val food = vm.food
        binding.apply {
            foodName.text = food?.name
            detailsPrice.text = "${food?.price} DA"
            if (food != null) {
                foodDetailsImage.setImageResource(food.image)
            }
            foodDescritpion.text = food?.description
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}