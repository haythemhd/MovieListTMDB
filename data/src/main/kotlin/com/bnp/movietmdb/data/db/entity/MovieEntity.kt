package com.bnp.movietmdb.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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

