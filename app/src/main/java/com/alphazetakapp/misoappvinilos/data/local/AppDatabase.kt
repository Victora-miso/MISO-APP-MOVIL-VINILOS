package com.alphazetakapp.misoappvinilos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import com.alphazetakapp.misoappvinilos.data.local.dao.CollectorDao
import com.alphazetakapp.misoappvinilos.data.local.dao.MusicianDao
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.Collector
import com.alphazetakapp.misoappvinilos.data.model.Musician

@Database(
    entities = [Album::class, Musician::class, Collector::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun musicianDao(): MusicianDao
    abstract fun collectorDao(): CollectorDao


}
