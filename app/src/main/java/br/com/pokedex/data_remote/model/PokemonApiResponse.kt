package br.com.pokedex.data_remote.model

import br.com.pokedex.model.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonApiResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("types")
    val types: List<PokemonType>
)