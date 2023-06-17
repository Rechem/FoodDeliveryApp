package com.example.fooddelieveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.OrdersListAdapter
import com.example.fooddelieveryapp.databinding.FragmentOrdersBinding
import com.example.fooddelieveryapp.models.CartItem

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    lateinit var ordersAdapter : OrdersListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrdersBinding.inflate(layoutInflater)
        val view = binding.root
        //binding.progressBar.visibility = View.VISIBLE
        loadData()
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider_16_vertical, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.ordersRecyclerView.addItemDecoration(dividerItemDecoration)
    }
    fun loadData():List<CartItem> {
        val data = mutableListOf<CartItem>()

        return data
    }
}