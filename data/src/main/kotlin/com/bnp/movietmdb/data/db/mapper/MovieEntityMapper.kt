package com.bnp.movietmdb.data.db.mapper

import com.bnp.movietmdb.data.db.entity.MovieEntity
import com.bnp.movietmdb.domain.model.MovieDetail


fun MovieDetail.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview =  overview,
    releaseDate =  releaseDate,
    voteAverage = voteAverage,
    runtime =    runtime,
    tagline =  tagline,
    homepage = homepage,
    genres = genres,
    countries = countries,
    productionCompanies = productionCompanies
)

fun MovieEntity.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title ?: "",
    posterUrl = posterUrl ?: "",
    overview = overview ?: "",
    releaseDate = releaseDate ?: "",
    voteAverage = voteAverage ?: 0.0,
    runtime = runtime ?: 0,
    tagline = tagline ?: "",
    homepage = homepage ?: "",
    genres = genres,
    countries = countries,
    productionCompanies = productionCompanies
)