package com.example.fooddelieveryapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.activities.LoginActivity
import com.example.fooddelieveryapp.adapters.CartItemAdapter
import com.example.fooddelieveryapp.databinding.FragmentCartBinding
import com.example.fooddelieveryapp.models.CartItemModal
import com.example.fooddelieveryapp.viewmodels.CartViewModel


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding;
    lateinit var items : List<CartItemModal>
    lateinit var cartViewModel: CartViewModel;
    val deliveryFee = 70;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.cart);

        cartViewModel = CartViewModel.getInstance();

        binding= FragmentCartBinding.inflate(layoutInflater)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(activity)
        items = loadData()
        binding.cartRecyclerView.adapter = CartItemAdapter(loadData(), activity as Context, cartViewModel);

        var sum : Int = 0;
        for (item in items){
            sum +=item.price *item.quantity
        }


        binding.deliveryFeesPrice.text = deliveryFee.toString()

        cartViewModel.readCartTotal.observe(requireActivity(), Observer {
            binding.mealsPrice.text = it.toString();

            binding.totalCart.text ="${((it)?.plus(deliveryFee))}"
        })

        cartViewModel.setCartTotal(sum);

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkoutBtn.setOnClickListener{
            val prefs = requireActivity().getSharedPreferences("connection", Context.MODE_PRIVATE)
            val isConnected = prefs.getBoolean("connected",false)
            if(isConnected)
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            else{
                val intent = Intent(activity, LoginActivity::class.java)
                requireActivity().startActivityForResult(intent, 76);
            }
        }

        binding.addNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_cookNoteFragment)
        }

//        binding.cartCrossBtn.setOnClickListener{
//            findNavController().popBackStack()
//        }

        binding.clearAllBtn.setOnClickListener {
            cartViewModel.clearCart();
            (binding.cartRecyclerView.adapter as CartItemAdapter).clearItems();
        }

    }

    fun loadData():MutableList<CartItemModal> {

        val data = mutableListOf<CartItemModal>()
        val dataBase = AppDatabase.getInstance(requireActivity())
        val carts = dataBase!!.getCartItemDao().getAllItems();
        val TAG = "all"
        Log.i(TAG, carts.toString())
            data.addAll(
                cartViewModel.getCartItems().map {
                    CartItemModal(
                        id = it.mealId,
                        name = it.name,
                        image = it.image,
                        price = it.price,
                        quantity = it.quantity
                    )
                }
            )
        return data
    }

}
