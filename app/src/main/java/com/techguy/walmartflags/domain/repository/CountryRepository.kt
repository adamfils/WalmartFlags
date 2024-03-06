package com.techguy.walmartflags.domain.repository

import com.techguy.walmartflags.data.model.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
    fun saveCountryResponse(value: String)
    fun getCountryResponse(): String?
}