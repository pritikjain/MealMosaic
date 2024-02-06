package com.pritikjain.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.adapters.CategoryMealsAdapter
import com.pritikjain.easyfood.databinding.ActivityCategoryMealsBinding
import com.pritikjain.easyfood.fragments.HomeFragment
import com.pritikjain.easyfood.model.Meal
import com.pritikjain.easyfood.model.MealsByCategory
import com.pritikjain.easyfood.viewmodel.CategoryMealsViewModel
import com.pritikjain.easyfood.viewmodel.HomeViewModel

class CategoryMealsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCategoryMealsBinding

    private lateinit var categoryMealsViewModel: CategoryMealsViewModel

    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        // get category name data here and pass it to categoryMealsViewModel
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        observerMealsLiveData()

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun observerMealsLiveData() {
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
                binding.tvCategoryCount.text = mealsList.size.toString()
                categoryMealsAdapter.setMealsList(mealsList)
            })


    }
}