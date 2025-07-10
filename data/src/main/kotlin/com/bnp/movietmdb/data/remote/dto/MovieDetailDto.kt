package com.bnp.movietmdb.data.remote.dto

import com.bnp.movietmdb.domain.model.MovieDetail
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


fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        runtime = runtime,
        tagline = tagline,
        homepage = homepage,
        genres = genres.map { it.name },
        countries = productionCountries.map { it.name },
        productionCompanies = productionCompanies.map { it.name }
    )
}

