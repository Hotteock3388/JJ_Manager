package com.depotato.jubjub_manager.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPref(private val context: Context) {

    fun isExist(key: String): Boolean = getPref().contains(key)

    private fun getPref(): SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        getPref().edit().let {
            it.putString(key, value)
            it.apply()
        }
    }

    fun getDataString(key: String): String {
        return getPref().getString(key, "")!!
    }

    fun removeData(key: String){
        getPref().edit().remove(key).apply()

    }

}