package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelieveryapp.databinding.ActivityCookNoteBinding

class CookNote : AppCompatActivity() {

    lateinit var binding: ActivityCookNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCookNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}