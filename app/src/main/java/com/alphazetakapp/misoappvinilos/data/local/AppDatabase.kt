package com.alphazetakapp.misoappvinilos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `collectors` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `email` TEXT NOT NULL, `name` TEXT NOT NULL, `telephone` TEXT NOT NULL)")
                //db.execSQL("INSERT INTO `collectors` (`id`, `email`, `name`, `telephone`) SELECT `id`, `email`, `name`, `telephone` FROM `collectors`")
                //db.execSQL("ALTER TABLE `collectors` RENAME TO `collector`")
                //db.execSQL("CREATE TABLE IF NOT EXISTS `musicians` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `birthDate` TEXT NOT NULL, `description` TEXT NOT NULL, `image` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
                //db.execSQL("INSERT INTO `musicians` (`id`, `birthDate`, `description`, `image`, `name`) SELECT `id`, `birthDate`, `description`, `image`, `name` FROM `Musicians`")
                //db.execSQL("ALTER TABLE `musicians` RENAME TO `Musicians`")
                //db.execSQL("CREATE TABLE IF NOT EXISTS `album` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,`name` TEXT NOT NULL,`cover` TEXT NOT NULL, `releaseDate` TEXT NOT NULL,`description` TEXT NOT NULL,`genre` TEXT NOT NULL,`recordLabel` TEXT NOT NULL)")

            }
        }
    }
}


