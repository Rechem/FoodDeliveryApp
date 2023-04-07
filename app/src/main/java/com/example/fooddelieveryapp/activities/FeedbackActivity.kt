package com.example.fooddelieveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelieveryapp.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeedbackBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}