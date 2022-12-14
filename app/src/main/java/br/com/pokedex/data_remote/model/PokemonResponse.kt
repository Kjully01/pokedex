package br.com.pokedex.data_remote.model

import br.com.pokedex.model.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)