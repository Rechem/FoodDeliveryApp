package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.Dao.CartItem
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.OrdersListAdapter
import com.example.fooddelieveryapp.adapters.RestaurantAdapter
import com.example.fooddelieveryapp.databinding.FragmentOrdersBinding
import com.example.fooddelieveryapp.models.Order
import com.example.fooddelieveryapp.models.OrderItem
import com.example.fooddelieveryapp.models.OrderVM
import com.example.fooddelieveryapp.models.RestauModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    lateinit var ordersAdapter : OrdersListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrdersBinding.inflate(layoutInflater)
        val view = binding.root
        val color = requireActivity().getColor(R.color.main_color)

        binding.progressBar.visibility = View.VISIBLE
        refreshOrders()
        loadData()
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        return view
    }

    private fun refreshOrders() {
        binding.refreshOrders.setOnRefreshListener {
            loadData()
            binding.refreshOrders.isRefreshing = false
            Toast.makeText(requireContext(),"Orders refreshed", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.ordersRecyclerView.addItemDecoration(dividerItemDecoration)
    }
    fun loadData() {
        binding.errorText.visibility = View.INVISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = requireActivity().getSharedPreferences("connection", Context.MODE_PRIVATE)
                val token = prefs.getString("token","")!!

                val response = Endpoint.createEndpoint(requireContext()).getOrders()
                withContext(Dispatchers.Main) {
                    binding.ordersRecyclerView.layoutManager = LinearLayoutManager(activity)
                    if (response.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        val orders = response.body()!!.map {
                            OrderItem(it.idOrder,it.restaurantName!!,it.date!!,it.totalPrice,it.status!!)
                        }
                        if (orders.isEmpty()){
                            binding.noOrders.visibility = View.VISIBLE
                        }else{
                            binding.noOrders.visibility = View.GONE
                        }
                        Log.i("orders",orders.toString())
                        ordersAdapter = OrdersListAdapter(orders, activity as Context)
                        binding.ordersRecyclerView.adapter = ordersAdapter
                        ordersAdapter.onItemClick = {
                            val vm = ViewModelProvider(requireActivity())[OrderVM::class.java]
                            vm.order = it
                            findNavController().navigate(R.id.action_ordersFragment_to_orderDetailsFragment)
                        }
                    } else {
                        throw Exception("Failed to load restaurants, error code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                }
                Log.i("exception", "Failed to reach server")
            } catch (e: Exception) {
                Log.e("Exception","exception",e)
            }
        }
    }
}