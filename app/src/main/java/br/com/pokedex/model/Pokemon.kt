package br.com.pokedex.model

data class Pokemon(
    val number: Int,
    val name: String,
    val types: List<PokemonType>
)