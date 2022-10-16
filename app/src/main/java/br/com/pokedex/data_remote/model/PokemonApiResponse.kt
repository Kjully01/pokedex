package br.com.pokedex.data_remote.model

import com.google.gson.annotations.SerializedName

data class PokemonApiResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("types")
    val types: List<PokemonTypeResponse>
)