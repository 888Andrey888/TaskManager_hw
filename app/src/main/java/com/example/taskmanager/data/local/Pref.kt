package com.example.taskmanager.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(private val context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    fun isOnBoardingShowed(): Boolean {
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun getName(): String? {
        return pref.getString(PREF_NAME, "")
    }

    fun getImage(): String? {
        return pref.getString(PREF_IMG, "")
    }

    fun setImage(url: String){
        pref.edit().putString(PREF_IMG, url).apply()
    }

    fun setName(name: String){
        pref.edit().putString(PREF_NAME, name).apply()
    }

    fun onBoardingShowed() {
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED_KEY = "seen.key"
        const val PREF_IMG = "pref.img"
    }
}