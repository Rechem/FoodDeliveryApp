package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.fooddelieveryapp.Dao.Endpoint
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityAvatarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody


class AvatarActivity : AppCompatActivity() {
     lateinit var binding : ActivityAvatarBinding
     var image : File? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.skip.setOnClickListener {
            this.finish()
        }
        binding.avatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
        binding.confirm.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val requestFile = image?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("avatar", image?.name, requestFile!!)
                val response = Endpoint.createEndpoint(baseContext).updateAvatar(imagePart)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.i("success", "avatar updated")
                        Toast.makeText(baseContext,"Avatar updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.i("error code","${response.code()}")
                        throw Exception(response.message())
                    }
                }
            }
            Log.i("exception", "Failed to update avatar")
            this.finish()
        }
        binding.reset.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.picker)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1 && resultCode== RESULT_OK){
            image = uriToFile(this,data?.data!!)
            binding.avatar.setImageURI(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = createImageFile(context)
        file.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        inputStream?.close()
        return file
    }

    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}