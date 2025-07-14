package com.bnp.movietmdb.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bnp.movietmdb.domain.model.MovieDetail

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val posterUrl: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val runtime: Int?,
    val tagline: String?,
    val homepage: String?,
    val genres: List<String>?,
    val countries: List<String>?,
    val productionCompanies: List<String>?
)


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