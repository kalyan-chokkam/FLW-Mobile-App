package org.piramalswasthya.sakhi.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StringListConverter {

    @TypeConverter
    fun toList(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}