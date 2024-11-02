package com.alphazetakapp.misoappvinilos.data.remote.dto

import com.alphazetakapp.misoappvinilos.data.model.Musician
import com.google.gson.annotations.SerializedName

//Estructura de respuesta de la api de vinilos
data class MusicianDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("birthDate") val birthDate: String,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String
)

fun MusicianDTO.toMusician(): Musician {
    return Musician(
        id = id,
        name = name,
        birthDate = birthDate,
        image = image,
        description = description
    )
}

