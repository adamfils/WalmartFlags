package com.techguy.walmartflags.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techguy.walmartflags.databinding.ActivityMainBinding
import com.techguy.walmartflags.domain.model.CountriesResult
import com.techguy.walmartflags.presentation.adapters.CountryAdapter
import com.techguy.walmartflags.presentation.viewmodel.CountryViewModel
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: CountryViewModel
    private var countryAdapter = CountryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvCountries.layoutManager = LinearLayoutManager(this)
        binding.rvCountries.adapter = countryAdapter
        binding.rvCountries.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CountryViewModel(applicationContext) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(CountryViewModel::class.java)

        viewModel.countries.observe(this) { result ->
            when (result) {
                is CountriesResult.Success -> {
                    // Hide loading indicator and update UI with the list of countries
                    binding.progressBar.visibility = View.GONE
                    countryAdapter.setCountries(result.data)
                }

                is CountriesResult.Loading -> {
                    // Show loading indicator
                    binding.progressBar.visibility = View.VISIBLE
                    Toasty.info(this, "Loading countries...", Toasty.LENGTH_SHORT).show()
                }

                is CountriesResult.Error -> {
                    // Hide loading indicator and show error message
                    binding.progressBar.visibility = View.GONE
                    Toasty.error(
                        this,
                        result.exception.message ?: "An error occurred",
                        Toasty.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}