package com.alphazetakapp.misoappvinilos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import com.alphazetakapp.misoappvinilos.data.model.Album

@Database(
    entities = [Album::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}
