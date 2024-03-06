package com.techguy.walmartflags.domain.repository

interface PreferencesRepository {
    fun saveCountryData(value: String)
    fun getCountryData(): String?
}