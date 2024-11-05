package com.example.weatherapp.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PrefRepository(val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences("WeatherPref", Context.MODE_PRIVATE)

    private val editor = pref.edit()
    val PREF_SELECTED_ID = "PREF_SELECTED_ID"
    private val gson = Gson()
    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)
    fun clearData() {
        editor.clear()
        editor.commit()
    }

    fun setSelectedId(selectedId: Int) {
        PREF_SELECTED_ID.put(selectedId)
    }

    fun getSelectedId() = PREF_SELECTED_ID.getInt()
}