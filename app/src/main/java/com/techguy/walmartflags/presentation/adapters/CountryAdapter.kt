package com.techguy.walmartflags.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techguy.walmartflags.data.model.Country
import com.techguy.walmartflags.databinding.CountryItemBinding

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private var countries = ArrayList<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    override fun getItemCount() = countries.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CountryItemBinding.bind(itemView)
        fun bind(country: Country) {
            binding.tvCountryName.text = "${country.name}, ${country.region}    ${country.code}"
            binding.tvCountryCapital.text = country.capital
        }
    }

    fun setCountries(countries: List<Country>) {
        this.countries = countries.toCollection(ArrayList())
        notifyDataSetChanged()
    }
}