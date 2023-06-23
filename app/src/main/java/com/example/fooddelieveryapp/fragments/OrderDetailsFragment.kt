package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.FoodAdapter
import com.example.fooddelieveryapp.adapters.ItemPriceAdapter
import com.example.fooddelieveryapp.adapters.RestaurantAdapter
import com.example.fooddelieveryapp.databinding.FragmentFoodBinding
import com.example.fooddelieveryapp.databinding.FragmentOrderDetailsBinding
import com.example.fooddelieveryapp.databinding.FragmentRestaurantsBinding
import com.example.fooddelieveryapp.models.CartItemModal
import com.example.fooddelieveryapp.models.OrderItem
import com.example.fooddelieveryapp.models.OrderMeal
import com.example.fooddelieveryapp.models.OrderVM
import com.example.fooddelieveryapp.models.RestauModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderDetailsFragment : Fragment() {
    lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var itemPriceAdapter : ItemPriceAdapter
    lateinit var vm: OrderVM;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity())[OrderVM::class.java];
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            "order#${vm.order!!.id}"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrderDetailsBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = vm.order
        loadData(order!!.id)

    }


    private fun loadData(orderNumber : Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = requireActivity().getSharedPreferences("connection", Context.MODE_PRIVATE)
                val token = prefs.getString("token","")!!
                Log.i("tokenRate",token!!)

                val response = Endpoint.createEndpoint(token).getOrder(orderNumber)
                withContext(Dispatchers.Main) {
                    binding.itemPriceList.layoutManager = LinearLayoutManager(activity)
                    if (response.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        val order = response.body()
                        val meals = order!!.meals.map{
                            OrderMeal(it.name,it.price,it.quantity)
                        }
                        itemPriceAdapter = ItemPriceAdapter(meals,activity as Context)
                        binding.itemPriceList.adapter = itemPriceAdapter
                        binding.date.text = order.date
                        binding.status.text =order.status
                        binding.totalSum.text = order.totalPrice.toString()
                        binding.delivererNom.text = order.deliverer.lastName
                        binding.delivererPrenom.text = order.deliverer.firstName
                        binding.delivererTelephone.text = order.deliverer.phoneNumber
                    } else {
                        throw Exception("Failed to load order details, error code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                }
                Log.i("exception", "Failed to reach server")
            } catch (e: Exception) {
                // Handle other types of exceptions here
            }
        }
    }


}