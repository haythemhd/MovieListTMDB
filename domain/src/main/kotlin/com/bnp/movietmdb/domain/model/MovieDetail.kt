package com.bnp.movietmdb.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int,
    val tagline: String?,
    val homepage: String?,
    val genres: List<String>,
    val countries: List<String>,
    val productionCompanies: List<String>
)