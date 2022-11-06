package br.com.pokedex.data_local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.pokedex.data_local.model.PokemonLocal

@Dao
interface PokemonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoritePokemon(pokemonLocal: PokemonLocal)

    @Delete
    suspend fun deleteFavoritePokemon(pokemonLocal: PokemonLocal)

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun listAllFavoritePokemons(): List<PokemonLocal>

    @Query("SELECT id FROM pokemon_table WHERE id = :id")
    fun searchPokemon(id: Int): Int

//    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
//    fun readAllData(): LiveData<List<PokemonLocal>>
}