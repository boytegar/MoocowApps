package id.boytegar.moocow.helper

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import id.boytegar.moocow.db.entity.MenuItem


class DataConverter {

    @TypeConverter
    fun fromListToStringMenu(menu: List<MenuItem>?): String? {
        if (menu == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MenuItem>>() {

        }.type
        return gson.toJson(menu, type)
    }

    @TypeConverter
    fun fromStringToListMenu(menu: String?): List<MenuItem>? {
        if (menu == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MenuItem>>() {

        }.type
        return gson.fromJson(menu, type)
    }
}