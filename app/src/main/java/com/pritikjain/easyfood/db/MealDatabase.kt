package com.pritikjain.easyfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pritikjain.easyfood.model.Meal
import okhttp3.internal.Version

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealsTypeConverter::class)
abstract class MealDatabase : RoomDatabase(){


    // doa interface
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile // if anything change in this field then it will be visible to every other thread
        var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase{
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }

    }

}