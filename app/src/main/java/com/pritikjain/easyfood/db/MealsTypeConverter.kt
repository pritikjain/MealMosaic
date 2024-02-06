package com.pritikjain.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealsTypeConverter {

    @TypeConverter
    fun fromAnyToString(attributes: Any?): String
    {
        if(attributes == null)
            return ""
        return attributes.toString()
    }

    @TypeConverter
    fun fromStringToAny(attributes: String?): Any
    {
        if(attributes == null)
            return ""
        return attributes
    }
}