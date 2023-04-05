package com.example.fooddelieveryapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class Util {
    fun openPage(ctx: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(intent)
    }
    fun openLocation(ctx : Context, uri:String){
        val gmmIntentUri = Uri.parse(uri)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        ctx.startActivity(mapIntent)
    }
    fun dial(ctx: Context, uri : String){
        val intentUri = Uri.parse("tel: $uri")
        val callIntent = Intent(Intent.ACTION_DIAL, intentUri)
        ctx.startActivity(callIntent)
    }
    fun email(ctx:Context,email : String){
        val intentUri = Uri.parse("mailto:")
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = intentUri
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "subject")
            putExtra(Intent.EXTRA_TEXT, "body")
        }
        ctx.startActivity(emailIntent)
    }

}