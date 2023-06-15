package com.example.fooddelieveryapp.fragments

import android.content.Context
import com.example.fooddelieveryapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.databinding.FragmentCheckoutBinding
import com.example.fooddelieveryapp.models.CartItem
import com.example.fooddelieveryapp.models.Meal
import com.example.fooddelieveryapp.models.OrderInfo
import com.example.fooddelieveryapp.models.UserConnexion
import com.example.fooddelieveryapp.utils.CartModel
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
            CoroutineScope(Dispatchers.IO).launch {
                val orderInfo = OrderInfo(null,
                    binding.deliveryAddressEditText.text.toString(),
                    binding.deliveryNoteEditText.text.toString(),
                    meals
                )
                val response = Endpoint.createEndpoint(requireContext()).order(orderInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        //binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root,"Order submitted", Snackbar.LENGTH_LONG).show()
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