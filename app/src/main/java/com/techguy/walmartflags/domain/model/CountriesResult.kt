package com.techguy.walmartflags.domain.model

sealed class CountriesResult<out R> {
    data class Success<out T>(val data: T) : CountriesResult<T>()
    data class Error(val exception: Exception) : CountriesResult<Nothing>()
    data object Loading : CountriesResult<Nothing>()
}
