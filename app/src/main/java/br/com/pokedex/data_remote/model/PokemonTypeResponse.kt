package br.com.pokedex.data_remote.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeResponse(
    @SerializedName("type")
    val type: TypeResponse
)