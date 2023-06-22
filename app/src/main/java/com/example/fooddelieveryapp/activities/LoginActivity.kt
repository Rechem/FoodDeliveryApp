package com.example.fooddelieveryapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.ContinuationInterceptor


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var togglePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            this.startActivity(intent)
        }
        binding.eye.setOnClickListener {
            if(!togglePassword){
                binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePassword = true
            }else{
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePassword = false
            }
        }
        binding.loginBtn.setOnClickListener{
            val activityContext = this
            CoroutineScope(Dispatchers.IO).launch {
                val connexionInfo = UserConnexion(binding.email.text.toString(),binding.password.text.toString())
                val response = Endpoint.createEndpoint(baseContext).login(connexionInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userInfo = response.body()
                        val prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)
                        val email = binding.email.text.toString()
                        val username = userInfo?.username
                        val password = binding.password.text.toString()
                        prefs.edit{
                            putInt("idUser",userInfo!!.idUser)
                            putString("username",username)
                            putString("email",email)
                            putString("avatar",userInfo.avatar)
                            putString("token",userInfo.token)
                            putString("password",password)
                            putBoolean("connected",true)
                        }
                        Log.i("avatar",userInfo!!.avatar)
                        Toast.makeText(baseContext,"Connected! as $username", Toast.LENGTH_LONG).show()
                        val intent = Intent(activityContext, MainActivity::class.java)
                        activityContext.startActivity(intent)
                    } else {
                        Snackbar.make(view,"wrong email or password", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.googleBtn.setOnClickListener{

        }
    }
}