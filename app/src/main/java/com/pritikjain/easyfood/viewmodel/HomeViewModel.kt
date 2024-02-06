package com.pritikjain.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pritikjain.easyfood.endpoint.RetrofitInstance
import com.pritikjain.easyfood.model.Category
import com.pritikjain.easyfood.model.CategoryList
import com.pritikjain.easyfood.model.MealsByCategoryList
import com.pritikjain.easyfood.model.MealsByCategory
import com.pritikjain.easyfood.model.Meal
import com.pritikjain.easyfood.model.MealList
import com.pritikjain.easyfood.respository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val mealsRepository: MealsRepository
):ViewModel(

) {

    // Mutable Live data means : you can change this value
    private var randomMealLiveData = MutableLiveData<Meal>()

    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()

    private var categoriesLiveData = MutableLiveData<List<Category>>();

    private var favoritesMealsLiveData = mealsRepository.getAllMeals()

    private val searchMealsLiveData = MutableLiveData<List<Meal>>();

    // 1st : One way to Make sure during configuration change my Meals data don't get change is
    // Remove : viewModel.getRandomMeal() from HomeFragment and add getRandomMeal in init() block of ViewModel
//    init {
//        getRandomMeal()
//    }

    // 2nd :  Second way  : Make sure during configuration change my Meals data don't get change is
    // nothing to be added here but in HomeFragment add private var saveStateRandomMeal : Meal? = null
    // And open viewModel.getRandomMeal() in HomeFragment

    private var saveStateRandomMeal : Meal? = null
    fun getRandomMeal() {
        saveStateRandomMeal?.let { randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return;

        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    Log.d("TEST", "meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
//                    Glide.with(this@HomeFragment)
//                        .load(randomMeal.strMealThumb)
//                        .into(binding.imgRandomMeal)
                    randomMealLiveData.value = randomMeal
                    saveStateRandomMeal = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment getRandomMeal", t.message.toString())
            }
        })
    }



    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment getPopularItems()", t.message.toString())
            }

        })

    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let{ categoryList ->
                     categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.e("HomeFragment getCategories()", t.message.toString())
            }

        })
    }

    fun searchMeals(searchQuery: String){
        RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let { mealList ->
                    mealList.meals?.let {
                        searchMealsLiveData.postValue(it)
                    }

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFragment searchMeals()", t.message.toString())
            }

        })
    }


    fun observeRandomMealLiveData(): LiveData<Meal> {  // LiveData : You can only read this data and not write anything init
        return randomMealLiveData;
    }

    fun observePopularItemsLiveData() : LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData() : LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observerFavoritesLiveData() : LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }

    fun observerSearchMealsLiveData() : LiveData<List<Meal>> {
        return searchMealsLiveData
    }



    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealsRepository.deleteMeal(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealsRepository.insertMeal(meal)
            //mealsDatabase.mealDao().upsert(meal)
        }
    }


}


