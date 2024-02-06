package com.pritikjain.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pritikjain.easyfood.db.MealDatabase
import com.pritikjain.easyfood.endpoint.RetrofitInstance
import com.pritikjain.easyfood.model.Meal
import com.pritikjain.easyfood.model.MealList
import com.pritikjain.easyfood.respository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
   // private val mealsDatabase: MealDatabase
):ViewModel() {

    private var mealDetailsLiveData =  MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val meal = response.body()?.meals?.first()
                    meal?.let { meal->
                        mealDetailsLiveData.postValue(meal)
                    }
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("MealActivity",t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealsRepository.insertMeal(meal)
            //mealsDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealsRepository.deleteMeal(meal)
           // mealsDatabase.mealDao().delete(meal)
        }
    }

}