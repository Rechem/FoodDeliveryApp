package com.example.fooddelieveryapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.R
import com.example.fooddelieveryapp.databinding.ActivityMainBinding
import com.example.fooddelieveryapp.fragments.OrdersFragment
import com.example.fooddelieveryapp.fragments.RestaurantsFragment
import com.example.fooddelieveryapp.fragments.SettingsFragment
import com.example.fooddelieveryapp.models.Restaurant
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var prefs: SharedPreferences
    private lateinit var drawerLayout : DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("connection", Context.MODE_PRIVATE)


        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerLayout  = binding.drawerLayout
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,RestaurantsFragment()).commit()
            binding.navView.setCheckedItem(R.id.restaurants)
        }

        prefs.apply {
            if (!this.contains("connected"))
                this.edit {
                    putBoolean("connected",false)
                }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.restaurants -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                RestaurantsFragment()
            ).commit()
            R.id.orders -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                OrdersFragment()
            ).commit()
            R.id.settings -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                SettingsFragment()
            ).commit()
            R.id.nav_logout -> {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
                prefs.edit{
                    putBoolean("connected",false)
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    init {
        AppDatabase.buildDatabase(this)
    }
}