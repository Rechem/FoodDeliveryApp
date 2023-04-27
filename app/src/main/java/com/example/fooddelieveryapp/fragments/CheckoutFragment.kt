package com.example.fooddelieveryapp.fragments

import com.example.fooddelieveryapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.databinding.FragmentCheckoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CheckoutFragment : BottomSheetDialogFragment() {

    lateinit var binding:FragmentCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.crossBtn.setOnClickListener{
            findNavController().popBackStack()
        }

    }

    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }
}