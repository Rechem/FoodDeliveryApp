package com.example.fooddelieveryapp.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityMainBinding
import com.example.fooddelieveryapp.fragments.OrdersFragment
import com.example.fooddelieveryapp.fragments.RestaurantsFragment
import com.example.fooddelieveryapp.fragments.SettingsFragment
import com.example.fooddelieveryapp.models.Restaurant
import com.example.fooddelieveryapp.utils.API_URL
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var prefs: SharedPreferences
    lateinit var navController: NavController

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 76) {
//            Log.d("TAG", "after hours")
//            if (resultCode == RESULT_OK) {
//                // The method you want to run after ActivityB finishes
//                val headerView = binding.navView.getHeaderView(0)
//                val profilePic = headerView.findViewById<ImageView>(R.id.profile_image)
//                val avatar = data!!.getStringExtra("avatar")
//                Log.i("avatar",avatar!!)
//                Log.i("avatar","$API_URL/$avatar")
//
//                Log.d("TAG", "after hours2 $API_URL/$avatar")
//                Glide.with(this)
//                        .load("$API_URL/$avatar")
//                        .into(profilePic)
//          }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppDatabase.getInstance(this)
        prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        // FOR DRAWER
        prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)

        val headerView = binding.navView.getHeaderView(0)
        val profilePic = headerView.findViewById<ImageView>(R.id.profile_image)
        profilePic.setOnClickListener {
            val connected = prefs.getBoolean("connected",false)
            if(connected){
                val intent = Intent(this, AvatarActivity::class.java)
                startActivity(intent);
            }else{
                Snackbar.make(view,"You need to login to change your avatar", Snackbar.LENGTH_LONG).show()
            }
        }
        val connected = prefs.getBoolean("connected",false)
        if (connected) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
        val avatar = prefs.getString("avatar","")
        Log.i("avatar",avatar!!)
        if(avatar==""){
            profilePic.setImageResource(R.drawable.w)
        }else{
            Log.i("avatar","$API_URL/$avatar")
            Glide.with(this)
                .load("$API_URL/$avatar")
                .into(profilePic)
        }

        val name = headerView.findViewById<TextView>(R.id.header_username)
        name.text = prefs.getString("username","")
        binding.navView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.restaurantsFragment-> {
                    Navigation.findNavController(this,R.id.navHost).navigate(R.id.restaurantsFragment)

                }
                R.id.ordersFragment-> {
                    Navigation.findNavController(this,R.id.navHost).navigate(R.id.ordersFragment)
                }
                R.id.logoutBtn -> {
                    Log.i("deconnexion", "logged out")
                    prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)
                    val connected = prefs.getBoolean("connected",false)
                    if(connected){
                        prefs.edit {
                            putBoolean("connected", false)
                        }
                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "You are not signed in", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        prefs.apply {
            if (!this.contains("connected"))
                this.edit {
                    putBoolean("connected", false)
                }
        }
        prefs.apply {
            if (!this.contains("note"))
                this.edit {
                    putBoolean("edited", false)
                }
        }

    }

    override fun onSupportNavigateUp() =
        super.onSupportNavigateUp() || NavigationUI.navigateUp(navController, binding.drawerLayout)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    // FOR NAVBAR
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.cartBtn -> {
                Navigation.findNavController(this,R.id.navHost).navigate(R.id.cartFragment)
            }
            R.id.loginBtn -> {
                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)

    }
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.restaurants -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
//                RestaurantsFragment()
//            ).commit()
//            R.id.orders -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
//                OrdersFragment()
//            ).commit()
//            R.id.settings -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
//                SettingsFragment()
//            ).commit()
//            R.id.nav_logout -> {
//                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
//                prefs.edit{
//                    putBoolean("connected",false)
//                }
//            }
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
}