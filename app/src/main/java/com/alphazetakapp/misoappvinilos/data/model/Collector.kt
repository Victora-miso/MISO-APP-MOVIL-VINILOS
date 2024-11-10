package com.alphazetakapp.misoappvinilos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collector")
data class Collector (
    @PrimaryKey
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
)