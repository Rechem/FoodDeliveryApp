package com.example.fooddelieveryapp.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityLoginBinding
import com.example.fooddelieveryapp.models.GoogleignInInfo
import com.example.fooddelieveryapp.models.SignUpInfo
import com.example.fooddelieveryapp.models.UserConnexion
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.ContinuationInterceptor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var togglePassword = false
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        oneTapClient = Identity.getSignInClient(this)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.web_client_id))
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .setRequestVerifiedPhoneNumber(true)
                    .build())
            .build()

        val activityResultLauncher : ActivityResultLauncher<IntentSenderRequest> = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult(), ActivityResultCallback { result->
                if(result.resultCode == RESULT_OK){
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                        val idToken = credential.googleIdToken
                        when {
                            idToken != null -> {
                                val name = credential.givenName
                                val email = credential.id
                                val phone = if (credential.phoneNumber!=null) credential.phoneNumber.toString()  else ""
                                Log.i("google","username : ${credential.givenName}")
                                Log.i("google","email : ${credential.id}")
                                Log.i("google","phone : ${credential.phoneNumber}")

                                val activityContext = this
                                CoroutineScope(Dispatchers.IO).launch {
                                    val info = GoogleignInInfo(
                                        name!!,
                                        email,
                                        phone
                                    )
                                    val response = Endpoint.createEndpoint(baseContext).googleSignIn(info)
                                    withContext(Dispatchers.Main) {
                                        if (response.isSuccessful) {
                                            val userInfo = response.body()
                                            val prefs = getSharedPreferences("connection", MODE_PRIVATE)
                                            val username = userInfo?.username
                                            prefs.edit{
                                                putInt("idUser",userInfo!!.idUser)
                                                putString("username",username)
                                                putString("email",email)
                                                putString("avatar",userInfo.avatar)
                                                putString("token",userInfo.token)
                                                putBoolean("connected",true)
                                                commit()
                                                apply()
                                            }
                                            Toast.makeText(baseContext,"Connected! as $username", Toast.LENGTH_LONG).show()
                                            val intent = Intent(activityContext, MainActivity::class.java)
                                            activityContext.startActivity(intent)
                                        } else {
                                            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                                            Snackbar.make(
                                                binding.root,
                                                errorMessage,
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                                Log.d(ContentValues.TAG, "Got ID token.")
                            }
                            else -> {
                                // Shouldn't happen.
                                Log.d(ContentValues.TAG, "No ID token!")
                            }
                        }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            })

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
            if(validateForm()){
                CoroutineScope(Dispatchers.IO).launch {
                    val connexionInfo = UserConnexion(binding.email.text.toString(),binding.password.text.toString())
                    val response = Endpoint.createEndpoint(baseContext).login(connexionInfo)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val userInfo = response.body()!!
                            val prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)
                            val username = userInfo.username
                            prefs.edit{
                                putString("username",username)
                                putString("avatar",userInfo.avatar)
                                putString("token",userInfo.token)
                                putBoolean("connected",true)
                                commit()
                                apply()
                            }
                            val token = prefs.getString("token","")
                            Log.i("token log",token!!)
                            Toast.makeText(baseContext,"Connected! as $username", Toast.LENGTH_LONG).show()
                            val intent = Intent(activityContext, MainActivity::class.java)
                            activityContext.startActivity(intent)
                        } else {
                            Snackbar.make(view,"wrong email or password", Snackbar.LENGTH_LONG).show()

                        }
                    }
                }
            }else{
                Snackbar.make(
                    binding.root,
                    "Please correct the errors in the form",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.googleBtn.setOnClickListener{
            Log.i("google","beginning")
            oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this) { result ->
                    val intentSenderRequest : IntentSenderRequest = IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender).build()
                    activityResultLauncher.launch(intentSenderRequest)
                    Log.i("google","success")
                }
                .addOnFailureListener(this) { e ->
                    // No Google Accounts found. Just continue presenting the signed-out UI.
                    Log.e("google","failure",e)
                    Log.d(ContentValues.TAG, e.localizedMessage!!)
                }
        }
    }


    private fun validateForm(): Boolean {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isEmpty()) {
            binding.email.error = "Email is required"
            return false
        }
        if (password.isEmpty()) {
            binding.password.error = "Password is required"
            return false
        }
        return true
    }
}