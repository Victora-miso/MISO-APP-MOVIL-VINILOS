package com.alphazetakapp.misoappvinilos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class Track(
    val name: String,
    val duration: String
)