package com.alphazetakapp.misoappvinilos.data.remote.dto

import com.alphazetakapp.misoappvinilos.data.model.Track
import com.google.gson.annotations.SerializedName

data class TrackDTO(
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: String
)

fun TrackDTO.toTrack(): Track {
    return Track(
        name = name,
        duration = duration
    )
}