package com.example.fooddelieveryapp.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // google on tap sign up
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
            Log.i(TAG, "onCreate: sign up listner")
            CoroutineScope(Dispatchers.IO).launch {
                val signUpInfo = SignUpInfo(binding.username.text.toString(),binding.email.text.toString(),binding.phone.text.toString(),binding.adresse.text.toString(),binding.password.text.toString())
                val response = Endpoint.createEndpoint(baseContext).signup(signUpInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        //binding.progressBar.visibility = View.GONE
                        val userInfo = response.body()
                        val prefs = getSharedPreferences("connection", MODE_PRIVATE)
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
                        Snackbar.make(binding.root,"Signed up as $username", Snackbar.LENGTH_LONG).show()
                        Snackbar.make(binding.root,"Connected! as $username", Snackbar.LENGTH_LONG).show()

                    } else {
                        throw Exception(response.message())
                    }
                }
            }
            this.finish()
        }
    }
}