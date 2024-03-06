package com.techguy.walmartflags.data.repository

import android.content.Context
import com.techguy.walmartflags.data.source.local.LocalDB.getCountryResponse
import com.techguy.walmartflags.data.source.local.LocalDB.saveCountryResponse
import com.techguy.walmartflags.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {

    override fun saveCountryData(value: String) {
        context.saveCountryResponse(value)
    }

    override fun getCountryData(): String? {
        return context.getCountryResponse()
    }
}