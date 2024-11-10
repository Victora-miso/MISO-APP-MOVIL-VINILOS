package com.alphazetakapp.misoappvinilos.data.remote.dto


import com.alphazetakapp.misoappvinilos.data.model.Collector
import com.google.gson.annotations.SerializedName

//Estructura de respuesta de la api de vinilos
data class CollectorDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("email") val email: String,
)

fun CollectorDTO.toCollector(): Collector {
    return Collector(
        id = id,
        name = name,
        telephone = telephone,
        email = email,
    )
}