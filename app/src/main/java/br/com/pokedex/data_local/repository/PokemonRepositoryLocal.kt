package br.com.pokedex.data_local.repository

import androidx.lifecycle.LiveData
import br.com.pokedex.data_local.PokemonDAO
import br.com.pokedex.data_local.model.PokemonLocal

class PokemonRepositoryLocal(private val pokemonDAO: PokemonDAO) {

//    val readPokemonFavorite: LiveData<List<PokemonLocal>> = pokemonDAO.readAllData()

    suspend fun addFavoritePokemon(pokemon: PokemonLocal) {
        pokemonDAO.addFavoritePokemon(pokemon)
    }

    suspend fun deleteFavoriteAnimal(pokemon: PokemonLocal) {
        pokemonDAO.deleteFavoritePokemon(pokemon)
    }

    suspend fun listAllFavoritePokemons(): List<PokemonLocal>{
        return pokemonDAO.listAllFavoritePokemons()
    }
}