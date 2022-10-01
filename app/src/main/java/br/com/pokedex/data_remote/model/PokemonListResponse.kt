package br.com.pokedex.data_remote.model

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results")
    val pokemonResponses: List<PokemonResponse>
)