package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityMainBinding
import com.example.fooddelieveryapp.utils.API_URL
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var prefs: SharedPreferences
    lateinit var navController: NavController
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
        val connected = prefs.getBoolean("connected",false)


        if (connected) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.drawer)
            val inflater = LayoutInflater.from(binding.navView.context)
            val headerView = inflater.inflate(R.layout.drawer_header, binding.navView, false)
            binding.navView.addHeaderView(headerView);
            val profilePic = headerView.findViewById<ImageView>(R.id.profile_image)
            profilePic.setOnClickListener {
                val intent = Intent(this, AvatarActivity::class.java)
                this.startActivity(intent)
            }

            val avatar = prefs.getString("avatar","")
            Glide.with(this)
                .load("$API_URL/$avatar")
                .into(profilePic)

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


                        binding.navView.menu.clear()
                        binding.navView.removeHeaderView(binding.navView.getHeaderView(0));
                        binding.navView.inflateMenu(R.menu.login_drawer)
                        setDisconnectedItemsMenu()
                    }

                }
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }else{
            setDisconnectedItemsMenu()
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

    private fun setDisconnectedItemsMenu(){
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.login_btn_drawer -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true;
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
}