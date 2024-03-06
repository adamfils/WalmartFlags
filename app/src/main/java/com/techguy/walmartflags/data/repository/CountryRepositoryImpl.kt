package com.techguy.walmartflags.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techguy.walmartflags.data.model.Country
import com.techguy.walmartflags.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val apiService: CountriesApi,
    private val context: Context
) : CountryRepository {

    companion object {
        private const val PREF_NAME = "flagpref"
        private const val COUNTRY_RESPONSE = "COUNTRY_RESPONSE"
    }

    override suspend fun getCountries(): List<Country> {
        // Try to get the local version first
        val localData = getCountryResponse()
        return if (localData != null) {
            // Convert the JSON string back to a List<Country>
            Gson().fromJson(localData, object : TypeToken<List<Country>>() {}.type)
        } else {
            // Fetch from network if local version is not available
            val response = apiService.getCountries()
            if (response.isSuccessful && response.body() != null) {
                val countries = response.body()!!
                // Save the fetched list to SharedPreferences
                saveCountryResponse(Gson().toJson(countries))
                countries
            } else {
                throw Exception("Error fetching countries")
            }
        }
    }

    override fun saveCountryResponse(value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(COUNTRY_RESPONSE, value)
            .apply()
    }

    override fun getCountryResponse(): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(COUNTRY_RESPONSE, null)
    }
}



