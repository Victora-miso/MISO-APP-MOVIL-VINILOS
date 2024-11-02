package com.alphazetakapp.misoappvinilos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import com.alphazetakapp.misoappvinilos.data.local.dao.MusicianDao
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.Musician

@Database(
    entities = [Album::class, Musician::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun musicianDao(): MusicianDao

//    companion object {
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE Musician ADD COLUMN newColumn TEXT")
//            }
//       }
//    }
}
