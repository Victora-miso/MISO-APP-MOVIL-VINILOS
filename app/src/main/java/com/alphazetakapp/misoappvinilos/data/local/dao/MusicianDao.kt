package com.alphazetakapp.misoappvinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphazetakapp.misoappvinilos.data.model.Musician

@Dao
interface MusicianDao {
    @Query("SELECT * FROM musicians")
    suspend fun getAllMusicians(): List<Musician>

    @Query("SELECT * FROM musicians WHERE id = :musicianId")
    suspend fun getMusicianById(musicianId: String): Musician?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musicians: List<Musician>)
}
