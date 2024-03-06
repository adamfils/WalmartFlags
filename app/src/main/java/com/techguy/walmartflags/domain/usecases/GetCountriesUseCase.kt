package com.techguy.walmartflags.domain.usecases

import com.google.gson.Gson
import com.techguy.walmartflags.data.model.Country
import com.techguy.walmartflags.domain.model.CountriesResult
import com.techguy.walmartflags.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetCountriesUseCase(private val repository: CountryRepository) {

    operator fun invoke(): Flow<CountriesResult<List<Country>>> = flow {
        try {
            emit(CountriesResult.Loading)
            val localData = repository.getCountryResponse()
            if (localData != null) {
                // Assuming you have a method to convert JSON string to List<Country>
                val countries = Gson().fromJson(localData, Array<Country>::class.java).toList()
                emit(CountriesResult.Success(countries))
            } else {
                val countries = repository.getCountries()
                val jsonString = Gson().toJson(repository.getCountries())
                // Optionally save the response for future use
                repository.saveCountryResponse(jsonString)
                emit(CountriesResult.Success(countries))
            }
        } catch (e: Exception) {
            emit(CountriesResult.Error(e))
        }
    }
}
