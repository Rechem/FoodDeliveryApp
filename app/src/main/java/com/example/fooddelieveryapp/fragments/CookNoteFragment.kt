package com.example.fooddelieveryapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.FragmentCookNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        val prefs = requireActivity().getSharedPreferences("note", Context.MODE_PRIVATE)
        val note = prefs.getString("cookNote", "")

        if(note != ""){
            binding.confirmNoteBtn.text = "Save"
            binding.cookNoteEditText.setText(note)
        }

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

            if(binding.cookNoteEditText.text.toString() != "") {
                if(prefs.getString("cookNote","") != ""){
                    Toast.makeText(requireContext(), "Note saved !", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Note added !", Toast.LENGTH_SHORT).show()
                }

            }
            prefs.edit {
                putString("cookNote", binding.cookNoteEditText.text.toString())
                putBoolean("edited",true)
            }
            findNavController().popBackStack()
        }

    }

    override fun getTheme(): Int {
        return R.style.NoBackgroundDialogTheme
    }

}