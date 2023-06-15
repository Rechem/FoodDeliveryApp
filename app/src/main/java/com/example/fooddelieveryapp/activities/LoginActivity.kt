package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.adapters.FoodAdapter
import com.example.fooddelieveryapp.databinding.ActivityLoginBinding
import com.example.fooddelieveryapp.models.FoodModel
import com.example.fooddelieveryapp.models.UserConnexion
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

            CoroutineScope(Dispatchers.IO).launch {
                val connexionInfo = UserConnexion(binding.email.text.toString(),binding.password.text.toString())
                val response = Endpoint.createEndpoint(baseContext).login(connexionInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        //binding.progressBar.visibility = View.GONE
                        val userInfo = response.body()
                        val prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)
                        val email = binding.email.text.toString()
                        val username = userInfo?.username
                        val password = binding.password.text.toString()
                        prefs.edit{
                            putInt("idUser",userInfo!!.idUser)
                            putString("username",username)
                            putString("email",email)
                            putString("token",userInfo.token)
                            putString("password",password)
                            putBoolean("connected",true)
                        }
                        Snackbar.make(binding.root,"Connected! as $username", Snackbar.LENGTH_LONG).show()

                    } else {
                        throw Exception(response.message())
                    }
                }
            }
            this.finish()
        }

        binding.googleBtn.setOnClickListener{

        }
    }
}