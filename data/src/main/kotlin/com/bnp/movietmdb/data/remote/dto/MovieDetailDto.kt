package com.bnp.movietmdb.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String? = null,
    val overview: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    val runtime: Int = 0,
    val tagline: String? = null,
    val homepage: String? = null,
    val genres: List<GenreDto> = emptyList(),
    @SerialName("production_companies") val productionCompanies: List<CompanyDto> = emptyList(),
    @SerialName("production_countries") val productionCountries: List<CountryDto> = emptyList()
)
@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class CompanyDto(
    val id: Int,
    val name: String
)

@Serializable
data class CountryDto(
    val name: String
)


