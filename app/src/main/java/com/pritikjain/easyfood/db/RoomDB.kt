package com.pritikjain.easyfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pritikjain.easyfood.model.Meal

@Database(
    entities =
        [Meal::class],
        version = 1,
        exportSchema = false)
@TypeConverters(MealsTypeConverter::class)
abstract class RoomDB :RoomDatabase() {

    // doa interfaces
    abstract  fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB =
            INSTANCE?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it }
            }

        private fun buildDatabase(context: Context):RoomDB {
            val db = Room.databaseBuilder(context.applicationContext,
                                            RoomDB::class.java,"RoomDB")
            return db.build()
        }

    }
}
