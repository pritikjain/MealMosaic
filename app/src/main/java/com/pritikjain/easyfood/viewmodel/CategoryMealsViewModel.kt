package com.pritikjain.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pritikjain.easyfood.endpoint.RetrofitInstance
import com.pritikjain.easyfood.model.Category
import com.pritikjain.easyfood.model.Meal
import com.pritikjain.easyfood.model.MealsByCategory
import com.pritikjain.easyfood.model.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String)
    {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object: Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {  mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.e("CategoryMealsView",t.message.toString())
            }

        })
    }

    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {  // LiveData : You can only read this data and not write anything init
        return mealsLiveData;
    }
}