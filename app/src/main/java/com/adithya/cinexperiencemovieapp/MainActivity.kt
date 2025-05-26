package com.adithya.cinexperiencemovieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavOptions
import com.adithya.cinexperiencemovieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigation: BottomNavigationView = binding.bottomNavigation

        bottomNavigation.setOnItemSelectedListener { item ->
            val options = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(navController.graph.startDestinationId, false)
                .build()

            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment, null, options)
                    true
                }
                R.id.movieListFragment -> {
                    navController.navigate(R.id.movieListFragment, null, options)
                    true
                }
                else -> false
            }
        }
    }
}
