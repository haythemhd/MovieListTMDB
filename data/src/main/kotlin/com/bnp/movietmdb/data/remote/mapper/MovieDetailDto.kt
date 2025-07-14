package com.bnp.movietmdb.data.remote.mapper

import com.bnp.movietmdb.data.remote.dto.MovieDetailDto
import com.bnp.movietmdb.domain.model.MovieDetail

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
