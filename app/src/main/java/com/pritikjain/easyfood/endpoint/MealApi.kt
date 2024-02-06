package com.pritikjain.easyfood.endpoint

import android.app.appsearch.SearchResults
import com.pritikjain.easyfood.model.CategoryList
import com.pritikjain.easyfood.model.MealsByCategoryList
import com.pritikjain.easyfood.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String): Call<MealList>

    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String): Call<MealsByCategoryList>

    //https://www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery:String): Call<MealList>

}