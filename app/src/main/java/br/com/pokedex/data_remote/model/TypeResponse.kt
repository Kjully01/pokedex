package br.com.pokedex.data_remote.model

import com.google.gson.annotations.SerializedName

data class TypeResponse (
    @SerializedName("name")
    val name: String
)