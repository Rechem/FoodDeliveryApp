package com.example.fooddelieveryapp.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivitySignupBinding
import com.example.fooddelieveryapp.models.SignUpInfo
import com.example.fooddelieveryapp.models.UserConnexion
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var togglePassword = false
    private var toggleConfirmPassword = false
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // google on tap sign up

        binding.eye1.setOnClickListener {
            if(!togglePassword){
                binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePassword = true
            }else{
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePassword = false
            }
        }
        binding.eye2.setOnClickListener {
            if(!togglePassword){
                binding.confirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                toggleConfirmPassword = true
            }else{
                binding.confirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                toggleConfirmPassword = false
            }
        }
        oneTapClient = Identity.getSignInClient(this)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.web_client_id))
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()

        val activityResultLauncher : ActivityResultLauncher<IntentSenderRequest> = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult(), ActivityResultCallback {result->
                if(result.resultCode == Activity.RESULT_OK){
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                        val idToken = credential.googleIdToken
                        when {
                            idToken != null -> {
                                val email = credential.id
                                Snackbar.make(binding.root,"Connected! as $email", Snackbar.LENGTH_SHORT).show()
                                Log.d(TAG, "Got ID token.")
                            }
                            else -> {
                                // Shouldn't happen.
                                Log.d(TAG, "No ID token!")
                            }
                        }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            })

        binding.loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
        }

        binding.googleSignupBtn.setOnClickListener {
            oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this) { result ->

                    val intentSenderRequest : IntentSenderRequest = IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender).build()
                    activityResultLauncher.launch(intentSenderRequest)

                }
                .addOnFailureListener(this) { e ->
                    // No Google Accounts found. Just continue presenting the signed-out UI.
                    Log.d(TAG, e.localizedMessage)
                }
        }
        binding.signupBtn.setOnClickListener {
            if (validateForm()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val signUpInfo = SignUpInfo(
                        binding.username.text.toString().trim(),
                        binding.email.text.toString().trim(),
                        binding.phone.text.toString().trim(),
                        binding.adresse.text.toString(),
                        binding.password.text.toString()
                    )
                    val response = Endpoint.createEndpoint().signup(signUpInfo)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // Handle successful sign-up
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
            } else {
                // Show validation error message
                Snackbar.make(
                    binding.root,
                    "Please correct the errors in the form",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val username = binding.username.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val phone = binding.phone.text.toString().trim()
        val address = binding.adresse.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()

        if (username.isEmpty()) {
            binding.username.error = "Username is required"
            return false
        }

        if (email.isEmpty()) {
            binding.email.error = "Email is required"
            return false
        }

        // Add more validation rules for other fields

        if (password.isEmpty()) {
            binding.password.error = "Password is required"
            return false
        }
        if (address.isEmpty()) {
            binding.adresse.error = "Address is required"
            return false
        }
        if (phone.isEmpty()) {
            binding.phone.error = "Phone is required"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPassword.error = "Confirm Password is required"
            return false
        }

        if (password != confirmPassword) {
            binding.confirmPassword.error = "Passwords do not match"
            return false
        }

        // Perform additional validation as per your requirements

        return true
    }
}