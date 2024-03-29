package com.techguy.walmartflags.data.source.local

import android.content.Context
import android.content.SharedPreferences

object LocalDB {

    private const val PREF_NAME = "flagpref"
    private const val COUNTRY_RESPONSE = "COUNTRY_RESPONSE"

    private fun Context.getSharedPreferences(name: String = PREF_NAME): SharedPreferences {
        return getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun Context.saveCountryResponse(value: String) {
        getSharedPreferences(PREF_NAME).edit().putString(COUNTRY_RESPONSE, value).apply()
    }

    fun Context.getCountryResponse(): String? {
        return getSharedPreferences(PREF_NAME).getString(COUNTRY_RESPONSE, null)
    }

}