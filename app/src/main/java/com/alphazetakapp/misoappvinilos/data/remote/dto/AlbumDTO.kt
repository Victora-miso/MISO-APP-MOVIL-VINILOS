package com.alphazetakapp.misoappvinilos.data.remote.dto

import com.alphazetakapp.misoappvinilos.data.model.Album
import com.google.gson.annotations.SerializedName

//Estructura de respuesta de la api de vinilos
data class AlbumDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("recordLabel") val recordLabel: String
)

fun AlbumDTO.toAlbum(): Album {
    return Album(
        id = id,
        name = name,
        cover = cover,
        releaseDate = releaseDate,
        description = description,
        genre = genre,
        recordLabel = recordLabel
    )
}