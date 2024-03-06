package com.techguy.walmartflags.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techguy.walmartflags.data.model.Country
import com.techguy.walmartflags.data.source.remote.RetrofitInstance
import com.techguy.walmartflags.domain.repository.CountryRepository
import com.techguy.walmartflags.domain.repository.PreferencesRepository

class CountryRepositoryImpl(private val preferencesRepository: PreferencesRepository) : CountryRepository {
    private val apiService = RetrofitInstance.api

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
        // Save the JSON string to SharedPreferences
        preferencesRepository.saveCountryData(value)
    }

    override fun getCountryResponse(): String? {
        // Get the saved JSON string from SharedPreferences
        return preferencesRepository.getCountryData()
    }
}

