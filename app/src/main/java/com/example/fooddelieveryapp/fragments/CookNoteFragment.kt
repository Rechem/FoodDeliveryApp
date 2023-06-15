package com.example.fooddelieveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentCartBinding

class CookNoteFragment : Fragment() {

    lateinit var binding: FragmentCartBinding;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

}