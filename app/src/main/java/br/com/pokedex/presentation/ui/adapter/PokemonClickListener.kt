package br.com.pokedex.presentation.ui.adapter

import br.com.pokedex.model.Pokemon

interface PokemonClickListener {

    fun onPokemonClickListener(pokemon: Pokemon)
    fun onFavoriteClickListener(pokemon: Pokemon)

}