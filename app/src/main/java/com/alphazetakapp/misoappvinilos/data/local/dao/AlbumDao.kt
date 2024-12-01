package com.alphazetakapp.misoappvinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.Track

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    suspend fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM album WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Int): Album?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)
}
