package com.example.fooddelieveryapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentFoodDetailsBinding
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.FoodModel
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.utils.CartModel

class FoodDetailsFragment : Fragment() {
    lateinit var binding: FragmentFoodDetailsBinding
    lateinit var dataBase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBase = Room.databaseBuilder(activity as Context, AppDatabase::class.java,"database")
            .build()
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
            addToCartBtn.setOnClickListener {
                val cartmodel = CartModel();
                cartmodel.CreateCart(dataBase.getCartDao(), food!!.restaurantId,1)
                cartmodel.addItemToCart(dataBase.getCartItemDao(),
                    food.restaurantId,dataBase.getCartDao().getCartById(1)[0].restaurantId!!,
                    1,food,quantity.text.toString().toInt())
            }
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cart.setOnClickListener {
            findNavController().navigate(R.id.action_foodDetailsFragment_to_cartFragment)
        }
    }
}