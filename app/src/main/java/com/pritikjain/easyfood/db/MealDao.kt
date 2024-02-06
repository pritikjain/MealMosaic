package com.pritikjain.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pritikjain.easyfood.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    //suspend fun insertMeal(meal: Meal)
    suspend fun upsert(meal: Meal)  // update and insert function in one function

//    @Update
//    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation" )
    fun getAllMeals(): LiveData<List<Meal>>

}