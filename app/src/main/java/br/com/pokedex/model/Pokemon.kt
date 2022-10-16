package br.com.pokedex.model

import br.com.pokedex.utils.Constants.IMAGE_URL

data class Pokemon(
    val number: Int,
    val name: String,
    val types: List<PokemonType>
) {
    val formattedNumber = number.toString().padStart(3, '0')

    val imageUrl = "$IMAGE_URL$formattedNumber.png"
}