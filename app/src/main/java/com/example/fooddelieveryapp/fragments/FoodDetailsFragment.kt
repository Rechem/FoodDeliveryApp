package com.example.fooddelieveryapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentFoodDetailsBinding
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.FoodModel
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.utils.API_URL
import com.example.fooddelieveryapp.utils.CartModel
import com.google.android.material.snackbar.Snackbar

class FoodDetailsFragment : Fragment() {
    lateinit var binding: FragmentFoodDetailsBinding
    lateinit var vm:FoodModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(requireActivity())[FoodModel::class.java]
        (requireActivity() as AppCompatActivity).supportActionBar?.title = vm.food!!.name
        binding= FragmentFoodDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val food = vm.food
        binding.apply {

            detailsPrice.text = "${food?.price} DA"
            if (food != null) {
                Glide.with(requireActivity())
                    .load(API_URL +"/"+food.picture)
                    .into(foodDetailsImage)
            }
            foodDescritpion.text = food?.description

            quantity.text = "1";
            addBtn.setOnClickListener {
                quantity.text = (quantity.text.toString().toInt()+1).toString()
            }
            minusBtn.setOnClickListener {
                if((quantity.text.toString().toInt()) > 1) {
                    quantity.text = (quantity.text.toString().toInt() - 1).toString()
                }
            }
            addToCartBtn.setOnClickListener {
                val cartmodel = CartModel.getInstance();
//                val cartId = cartmodel.createCart(
//                    dataBase!!.getCartDao(),
//                    food!!.idRestaurant,1
//                )
                val TAG = "add to cart"
                try {
                    cartmodel.addItemToCart(
                        food!!,
                        quantity.text.toString().toInt()
                    )

                    Toast.makeText(requireContext(), "${food.name} added to cart", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }catch (e:Exception){
                    Snackbar.make(binding.root,e.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
//        binding.back.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        binding.cart.setOnClickListener {
//
//            findNavController().navigate(R.id.action_foodDetailsFragment_to_cartFragment)
//        }
    }
}