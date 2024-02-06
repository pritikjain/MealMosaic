package com.pritikjain.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.databinding.ActivityMealBinding
import com.pritikjain.easyfood.viewmodel.HomeViewModel
import com.pritikjain.easyfood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Added this line for Hilt
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Step 1: Inflate bottom navigation
        val bottomNavigation =findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        // now we need to setup navController with bottom navigation
        NavigationUI.setupWithNavController(bottomNavigation,navController)



    }
}