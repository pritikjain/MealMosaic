package com.pritikjain.easyfood.respository

import android.app.Application
import androidx.lifecycle.LiveData
import com.pritikjain.easyfood.db.MealDao
import com.pritikjain.easyfood.db.RoomDB
import com.pritikjain.easyfood.endpoint.MealApi
import com.pritikjain.easyfood.model.Meal
import javax.inject.Inject

class MealsRepository @Inject constructor( application: Application ) {

    private val mealDao : MealDao

    init {
        mealDao = RoomDB.getInstance(application).mealDao() as MealDao
    }

    suspend fun insertMeal(meal: Meal) {
        mealDao.upsert(meal)
    }

    suspend fun deleteMeal(meal: Meal) {
        mealDao.delete(meal)
    }

    fun getAllMeals(): LiveData<List<Meal>> {
        return  mealDao.getAllMeals()
    }
}