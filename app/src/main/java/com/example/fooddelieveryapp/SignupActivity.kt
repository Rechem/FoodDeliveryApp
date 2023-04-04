package com.example.fooddelieveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelieveryapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.loginText.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            this.startActivity(intent)
        }
    }
}