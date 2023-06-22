package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.databinding.ActivityLoginBinding
import com.example.fooddelieveryapp.models.UserConnexion
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
            var valid = false
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
                        Toast.makeText(baseContext,"Connected! as $username", Toast.LENGTH_LONG).show()
                        valid = true
                    } else {
                        Snackbar.make(view,"wrong email or password", Snackbar.LENGTH_LONG).show()
                        valid = false
                    }
                }
            }
//            var returnIntent = Intent();
//            if(valid){
//                returnIntent.putExtra("avatar", savedAvatar);
//                returnIntent.putExtra("avatar", savedUsername);
//            }
//            setResult(76, returnIntent)
            this.finish()
        }

        binding.googleBtn.setOnClickListener{

        }
    }
}