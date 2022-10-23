package br.com.pokedex.data_remote.model

import com.google.gson.annotations.SerializedName

data class PokemonStatResponse(
    @SerializedName("base_stat")
    val baseState: Int,
    @SerializedName("stat")
    val stat: StatResponse
)