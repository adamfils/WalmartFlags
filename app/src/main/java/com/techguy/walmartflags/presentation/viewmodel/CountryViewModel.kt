package com.techguy.walmartflags.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techguy.walmartflags.data.model.Country
import com.techguy.walmartflags.data.repository.CountryRepositoryImpl
import com.techguy.walmartflags.data.source.remote.RetrofitInstance
import com.techguy.walmartflags.domain.model.CountriesResult
import com.techguy.walmartflags.domain.usecases.GetCountriesUseCase
import kotlinx.coroutines.launch

class CountryViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitInstance.api
    private val repository = CountryRepositoryImpl(apiService, context.applicationContext)
    private val getCountriesUseCase = GetCountriesUseCase(repository)

    private val _countries = MutableLiveData<CountriesResult<List<Country>>>()
    val countries: LiveData<CountriesResult<List<Country>>> = _countries

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            getCountriesUseCase().collect { result ->
                _countries.value = result
            }
        }
    }
}

