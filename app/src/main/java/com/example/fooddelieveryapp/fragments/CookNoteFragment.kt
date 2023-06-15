package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentCartBinding
import com.example.fooddelieveryapp.databinding.FragmentCookNoteBinding
import com.example.fooddelieveryapp.models.Meal
import com.example.fooddelieveryapp.models.OrderInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.databinding.FragmentCheckoutBinding
import com.example.fooddelieveryapp.models.CartItem

import com.example.fooddelieveryapp.models.UserConnexion
import com.example.fooddelieveryapp.utils.CartModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class CookNoteFragment : BottomSheetDialogFragment()  {

    lateinit var binding: FragmentCookNoteBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCookNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeCookNoteBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.closeCookNoteXBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.confirmNoteBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences("note", Context.MODE_PRIVATE)
            prefs.edit {
                putString("cookNote", binding.cookNoteEditText.text.toString())
                putBoolean("edited",true)
            }
            Toast.makeText(requireContext(), "logged out", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }

    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }

}