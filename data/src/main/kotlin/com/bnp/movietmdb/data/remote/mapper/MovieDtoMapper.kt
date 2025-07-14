package com.bnp.movietmdb.data.remote.mapper

import com.bnp.movietmdb.data.remote.dto.MovieDto
import com.bnp.movietmdb.domain.model.Movie


fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
)
