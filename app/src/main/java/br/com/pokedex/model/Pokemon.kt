package br.com.pokedex.model

import br.com.pokedex.utils.Constants.IMAGE_URL

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    var isFavorite: Boolean = false
) {
    val formattedNumber = id.toString().padStart(3, '0')

    val imageUrl = "$IMAGE_URL$formattedNumber.png"
}