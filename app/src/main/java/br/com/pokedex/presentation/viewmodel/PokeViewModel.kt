package br.com.pokedex.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import br.com.pokedex.data_local.PokemonDatabase
import br.com.pokedex.data_local.model.PokemonLocal
import br.com.pokedex.data_local.repository.PokemonRepositoryLocal
import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.data_remote.repository.PokemonRepositoryRemote
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.PokemonStat
import br.com.pokedex.model.PokemonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PokeViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryRemote: PokemonRepositoryRemote = PokemonRepositoryRemote()
    private var repositoryLocal: PokemonRepositoryLocal

    private val _pokemonsSuccess: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokemonsSuccess: LiveData<List<Pokemon>> = _pokemonsSuccess

    private val _pokemonSuccess: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemonSuccess: LiveData<Pokemon> = _pokemonSuccess

    lateinit var pokeApiResponse: PokemonApiResponse

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

//    var readPokemonFavorite: LiveData<List<PokemonLocal>>

    init {
        val pokemonDao = PokemonDatabase.getDatabase(
            application
        ).pokemonDAO()
        repositoryLocal = PokemonRepositoryLocal(pokemonDao)
//        readPokemonFavorite = repositoryLocal.readPokemonFavorite
    }

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRemote.getPokemons()
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect {

                    val pokemons = repositoryLocal.listAllFavoritePokemons()

                    it.pokemonResponses.let { listPokemon ->

                        val pokemons: List<Pokemon> = listPokemon.map { pokemonResult ->

                            val id =
                                pokemonResult.url
                                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                                    .replace("/", "").toInt()

                            repositoryRemote.getPokemon(id)
                                .catch { exception ->
                                    _error.postValue(exception.message)
                                }.collect { pokemonApiResponse ->
                                    pokeApiResponse = pokemonApiResponse
                                }

                            val pokemon = mapPokemon()

                            val pokemonExist = pokemons.find { pokemonLocal ->
                                pokemonLocal.id == pokemon.id
                            }

                            if (pokemonExist != null){
                                pokemon.isFavorite = true
                            }

                            pokemon

                        }

                        _pokemonsSuccess.postValue(pokemons)
                    }

                }
        }
    }

    fun getPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRemote.getPokemon(id)
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect {
                    pokeApiResponse = it

                    val pokemon = mapPokemon()

                    _pokemonSuccess.postValue(pokemon)
                }
        }
    }

    fun addOrDeletePokemon(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            val idPokemon = repositoryLocal.searchPokemon(pokemon.id)
            val pokeLocal = mapPokemonLocal(pokemon)

            if(pokemon.id == idPokemon){
                deleteFavoritePokemon(pokeLocal)
            } else {
                addFavoritePokemon(pokeLocal)
            }
        }
    }

    private fun addFavoritePokemon(pokemon: PokemonLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryLocal.addFavoritePokemon(pokemon)
        }
    }

    fun deleteFavoritePokemon(pokemon: PokemonLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryLocal.deleteFavoritePokemon(pokemon)
        }
    }

    private fun mapPokemon(): Pokemon {
        val pokemon = Pokemon(
            pokeApiResponse.id,
            pokeApiResponse.name,
            pokeApiResponse.types.map { type ->
                PokemonType(
                    type.type.name
                )
            },
            pokeApiResponse.stats.map { stat ->
                PokemonStat(
                    stat.stat.name,
                    stat.baseState
                )
            }
        )
        return pokemon
    }

    private fun mapPokemonLocal(poke: Pokemon): PokemonLocal {
        val pokemon = PokemonLocal(
            poke.id,
            poke.name,
//            poke.types,
//            poke.stats,
        )
        return pokemon
    }
}

