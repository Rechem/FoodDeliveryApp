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
import androidx.room.Room
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.activities.LoginActivity
import com.example.fooddelieveryapp.activities.SignupActivity
import com.example.fooddelieveryapp.adapters.CartItemAdapter
import com.example.fooddelieveryapp.databinding.ActivityCartBinding
import com.example.fooddelieveryapp.databinding.FragmentCartBinding
import com.example.fooddelieveryapp.models.CartItem
import com.example.fooddelieveryapp.utils.CartModel

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding


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
        val dataBase = AppDatabase.getInstance(requireActivity())
        val carts = dataBase!!.getCartItemDao().getAllItems();
        val TAG = "all"
        Log.i(TAG, carts.toString())
        val cartmodel =  CartModel()
       val restaurantId = 1
        val userId = 1
        if(
            cartmodel.checkIfCartExists(
                cartDao = dataBase!!.getCartDao(),
                restaurantId = restaurantId,
                userId= userId
            )
        )
            cartmodel.createCart(
                cartDao = dataBase.getCartDao(),
                restaurantId = restaurantId,
                ownerId = userId
            )
        else
            data.addAll(
                cartmodel.getCartItems(
                    cartDao = dataBase.getCartDao(),
                    cartItemDao = dataBase.getCartItemDao(),
                    restaurantId = restaurantId,
                    userId = userId
                ).map {
                    CartItem(
                        name = it.name,
                        image = it.image,
                        price = it.price,
                        quantity = it.quantity
                    )
                }
            )

//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))
//        data.add(CartItem("Yelena", R.drawable.w, 240.0, 2))


        return data
    }

}
