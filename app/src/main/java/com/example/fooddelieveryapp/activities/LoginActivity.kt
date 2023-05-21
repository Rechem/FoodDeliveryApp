package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            // verify if the account exists and get username
            val username = "toto"
            prefs.edit{
                putString("email",email)
                putString("password",password)
                putBoolean("connected",true)
            }
            Snackbar.make(binding.root,"Connected! as $username", Snackbar.LENGTH_LONG).show()
            this.finish()
        }

        binding.googleBtn.setOnClickListener{

        }
    }



}