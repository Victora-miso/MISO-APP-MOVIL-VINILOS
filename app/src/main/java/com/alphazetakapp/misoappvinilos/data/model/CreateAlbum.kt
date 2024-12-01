package com.alphazetakapp.misoappvinilos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "album")
data class CreateAlbum(
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: String,
    val recordLabel: String
)