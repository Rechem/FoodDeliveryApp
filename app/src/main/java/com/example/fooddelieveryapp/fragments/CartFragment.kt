package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.activities.LoginActivity
import com.example.fooddelieveryapp.activities.SignupActivity
import com.example.fooddelieveryapp.adapters.CartItemAdapter
import com.example.fooddelieveryapp.databinding.ActivityCartBinding
import com.example.fooddelieveryapp.databinding.FragmentCartBinding
import com.example.fooddelieveryapp.models.CartItem

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCartBinding.inflate(layoutInflater)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.cartRecyclerView.adapter = CartItemAdapter(loadData(), activity as Context)

        binding.mealsPrice.text = "69 DZD"
        binding.deliveryFeesPrice.text = "69 DZD"

        binding.totalCart.text = "4200 DZD"
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkoutBtn.setOnClickListener{
            val prefs = requireActivity().getSharedPreferences("connection", Context.MODE_PRIVATE)
            val isConnected = prefs.getBoolean("connected",false)
            if(isConnected)
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            else{
                val intent = Intent(activity, LoginActivity::class.java)
                this.startActivity(intent)

            }
        }

        binding.cartCrossBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    fun loadData():List<CartItem> {
        val data = mutableListOf<CartItem>()
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))
        data.add(CartItem("Yelena", R.drawable.w, 240, 2))

        return data
    }

}