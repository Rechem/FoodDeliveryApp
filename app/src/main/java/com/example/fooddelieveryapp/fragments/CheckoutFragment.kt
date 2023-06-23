package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.content.Intent
import com.example.fooddelieveryapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.activities.MainActivity
import com.example.fooddelieveryapp.databinding.FragmentCheckoutBinding
import com.example.fooddelieveryapp.models.Meal
import com.example.fooddelieveryapp.models.OrderInfo
import com.example.fooddelieveryapp.viewmodels.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CheckoutFragment : BottomSheetDialogFragment() {

    lateinit var binding:FragmentCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            requireActivity().resources.getString(R.string.checkout);
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
        binding.orderBtn.setOnClickListener {
            val dataBase = AppDatabase.getInstance(requireActivity())
            val meals = dataBase!!.getCartItemDao().getAllItems().map {
                Meal(it.mealId, it.quantity)
            }
            val prefs = requireActivity().getSharedPreferences("note", Context.MODE_PRIVATE)
            val edited = prefs.getBoolean("edited",false)
            var cookNote : String? = null
            if(edited){
                 cookNote = prefs.getString("cookNote",null)
            }
            val fbPrefs = requireActivity().getSharedPreferences("firebase", Context.MODE_PRIVATE)
            val fcmToken = fbPrefs.getString("fcmToken","")
            CoroutineScope(Dispatchers.IO).launch {
                val orderInfo = OrderInfo(cookNote,
                    binding.deliveryAddressEditText.text.toString(),
                    binding.deliveryNoteEditText.text.toString(),
                    meals,
                    fcmToken!!
                )
                val response = Endpoint.createEndpoint(requireContext()).order(orderInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        CartViewModel.getInstance().clearCart()
                        Toast.makeText(requireContext(),"Order submitted", Toast.LENGTH_LONG).show()
                        val intent = Intent(requireActivity(),MainActivity::class.java)
                        startActivity(intent)
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