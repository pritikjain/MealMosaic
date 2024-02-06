package com.pritikjain.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pritikjain.easyfood.R
import com.pritikjain.easyfood.databinding.ActivityMealBinding
import com.pritikjain.easyfood.db.MealDatabase
import com.pritikjain.easyfood.fragments.HomeFragment
import com.pritikjain.easyfood.model.Meal
import com.pritikjain.easyfood.respository.MealsRepository
import com.pritikjain.easyfood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId:String
    private lateinit var meanName:String
    private lateinit var mealThumb:String

    private lateinit var mealMVVM:MealViewModel
    private lateinit var youtubeLink: String

    private var mealToSave: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hilt update // I removed this code from Video 10
//        val mealDatabase = MealDatabase.getInstance(this)
//        val viewModelFactory = MealsViewModelFactory(mealDatabase)
//
//        //mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]
//        mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        // Added this line for Hilt
        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]



        getMeanInformationFromIntent()

        setInformationInView()

        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()

        // Youtube button Click
        onYoutubeButtonClick()

        // on fav click

        onFavoriteClick()
    }

    private fun onFavoriteClick() {
       binding.btnAddToFav.setOnClickListener{
           mealToSave?.let{
               mealMVVM.insertMeal(it)
               Toast.makeText(this,"Meal Saved", Toast.LENGTH_SHORT).show()
           }

       }
    }



    private fun onYoutubeButtonClick() {
        binding.imgYoutube.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this,object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal!!.strArea}"
                binding.tvInstructionsSteps.text=meal.strInstructions
                youtubeLink = meal.strYoutube.toString()
            }

        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = meanName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMeanInformationFromIntent() {
       val intent = intent

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        meanName =intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb =intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.progressBar.visibility =View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility =View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }

}