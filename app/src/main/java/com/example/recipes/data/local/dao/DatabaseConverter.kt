package com.example.recipes.data.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DatabaseConverter {

    @TypeConverter
    fun listOfStringToString(values: List<String>): String {
        return Gson().toJson(values)
    }

    @TypeConverter
    fun stringToListOfString(value: String): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

}