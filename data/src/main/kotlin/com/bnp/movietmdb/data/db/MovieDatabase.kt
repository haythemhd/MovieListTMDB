package com.bnp.movietmdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bnp.movietmdb.data.db.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase :  RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

