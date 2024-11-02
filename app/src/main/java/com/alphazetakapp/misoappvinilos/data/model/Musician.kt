package com.alphazetakapp.misoappvinilos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Musicians")
data class Musician(
    @PrimaryKey
    val id: String,
    val birthDate: String,
    val description: String,
    val image: String,
    val name: String
)