package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.FoodAdapter
import com.example.fooddelieveryapp.adapters.RestaurantAdapter
import com.example.fooddelieveryapp.databinding.FragmentFoodBinding
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.RestauModel
import com.example.fooddelieveryapp.models.FoodModel
import com.example.fooddelieveryapp.models.Restaurant
import com.example.fooddelieveryapp.utils.CartModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodFragment : Fragment() {
    lateinit var binding: FragmentFoodBinding
    private lateinit var foodAdpater : FoodAdapter
    lateinit var vm:RestauModel;
    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {

        vm = ViewModelProvider(requireActivity())[RestauModel::class.java];
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            vm.restau!!.name
        // Inflate the layout for this fragment
        binding= FragmentFoodBinding.inflate(layoutInflater)
        binding.rate.setOnClickListener {
            prefs = requireActivity().getSharedPreferences("connection", Context.MODE_PRIVATE)
            val connected = prefs.getBoolean("connected",false)
            if(connected){
                findNavController().navigate(R.id.action_foodFragment_to_ratingFragment)
            }else{
                Snackbar.make(binding.root,"You have to sign in to rate a restaurant", Snackbar.LENGTH_LONG).show()
            }
        }

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

        val restaurant = vm.restau
        loadData(restaurant!!)
    }

    private fun loadData(restaurant : Restaurant){
        CoroutineScope(Dispatchers.IO).launch {
            val response = Endpoint.createEndpoint(requireContext()).getMenus(restaurant.idRestaurant)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    //binding.progressBar.visibility = View.GONE
                    val menus = response.body()
                    binding.recyclerView.layoutManager = GridLayoutManager(activity,2)
                    foodAdpater = FoodAdapter(menus!!, activity as Context)
                    binding.recyclerView.adapter = foodAdpater
                    foodAdpater.onItemClick={
                        val vm = ViewModelProvider(requireActivity())[FoodModel::class.java]
                        vm.food = it
                        vm.restaurant = restaurant
                        findNavController().navigate(R.id.action_foodFragment_to_foodDetailsFragment)
                    }
                } else {
                    throw Exception("failed to load restaurants, error code : ${response.code()}")
                }
            }
        }
    }
}