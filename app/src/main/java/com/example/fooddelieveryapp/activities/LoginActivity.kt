package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.example.fooddelieveryapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            this.startActivity(intent)
        }
        binding.loginBtn.setOnClickListener{
            val prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)
            prefs.edit{
                putString("email",binding.email.text.toString())
                putString("password",binding.password.text.toString())
                putBoolean("connected",true)
            }
            Snackbar.make(binding.root,"je3fer je3fer", Snackbar.LENGTH_LONG).show()
            Log.i("je3fer", binding.password.text.toString())
            this.finish()
        }
    }

}