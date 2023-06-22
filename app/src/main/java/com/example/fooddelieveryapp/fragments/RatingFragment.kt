package com.example.fooddelieveryapp.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentRatingBinding
import com.example.fooddelieveryapp.models.Rating
import com.example.fooddelieveryapp.models.RestauModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RatingFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentRatingBinding
    lateinit var vm: RestauModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        vm = ViewModelProvider(requireActivity())[RestauModel::class.java];
        binding = FragmentRatingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.crossBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.rate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val rating = Rating(
                    vm.restau!!.idRestaurant,
                    binding.experience.text.toString().trim(),
                    binding.ratingBar.rating
                )
                val response = Endpoint.createEndpoint(requireContext()).rate(rating)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(),"Restaurant ${vm.restau!!.name} rated successfully", Toast.LENGTH_LONG).show()
                    } else {
                        throw Exception(response.message())
                    }
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }
}