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
    lateinit var binding: FragmentCartBinding;
    lateinit var cartmodel: CartModel;
    lateinit var items : List<CartItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartmodel = CartModel(AppDatabase.getInstance(requireActivity())!!.getCartItemDao());
        binding= FragmentCartBinding.inflate(layoutInflater)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(activity)
        items = loadData()
        binding.cartRecyclerView.adapter = CartItemAdapter(items, activity as Context);

        var sum : Int = 0;
        for (item in items){
            sum +=item.price *item.quantity
        }
        binding.mealsPrice.text = "${sum}"
        val deliveryFee = 70;
        binding.deliveryFeesPrice.text = "$deliveryFee"
        binding.totalCart.text = "${sum+deliveryFee}"


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

        binding.clearAllBtn.setOnClickListener {
            cartmodel.clearCart();
            (binding.cartRecyclerView.adapter as CartItemAdapter).clearItems();
            binding.mealsPrice.text = "0";
            binding.totalCart.text = binding.deliveryFeesPrice.text
        }

    }

    fun loadData():List<CartItem> {

        val data = mutableListOf<CartItem>()
        val dataBase = AppDatabase.getInstance(requireActivity())
        val carts = dataBase!!.getCartItemDao().getAllItems();
        val TAG = "all"
        Log.i(TAG, carts.toString())
            data.addAll(
                cartmodel.getCartItems().map {
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
