package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityMainBinding
import com.example.fooddelieveryapp.fragments.OrdersFragment
import com.example.fooddelieveryapp.fragments.RestaurantsFragment
import com.example.fooddelieveryapp.fragments.SettingsFragment
import com.example.fooddelieveryapp.models.Restaurant
import com.google.android.material.navigation.NavigationView

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

        prefs.apply {
            if (!this.contains("connected"))
                this.edit {
                    putBoolean("connected", false)
                }
        }

    }

    override fun onSupportNavigateUp() =
        super.onSupportNavigateUp() || NavigationUI.navigateUp(navController, binding.drawerLayout)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_logout -> {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
                prefs.edit {
                    putBoolean("connected", false)
                }
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