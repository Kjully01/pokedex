package br.com.pokedex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.data_remote.model.PokemonResponse
import br.com.pokedex.data_remote.repository.PokemonRepositoryRemote
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.PokemonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {

    private val repository: PokemonRepositoryRemote = PokemonRepositoryRemote()

    private val _pokemonsSuccess: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokemonsSuccess: LiveData<List<Pokemon>> = _pokemonsSuccess

    private val _pokemonSuccess: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemonSuccess: LiveData<Pokemon> = _pokemonSuccess

    lateinit var pokeApiResponse: PokemonApiResponse

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error


    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemons()
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect {

                    it.pokemonResponses.let {

                        val pokemons: List<Pokemon> = it.map { pokemonResult ->

                            val id =
                                pokemonResult.url
                                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                                    .replace("/", "").toInt()

                            repository.getPokemon(id)
                                .catch { exception ->
                                    _error.postValue(exception.message)
                                }.collect { pokemonApiResponse ->
                                    pokeApiResponse = pokemonApiResponse
                                }

                            Pokemon(
                                pokeApiResponse.id,
                                pokeApiResponse.name,
                                pokeApiResponse.types.map { type ->
                                    PokemonType(
                                        type.type.name
                                    )
                                }
                            )

                        }

                        _pokemonsSuccess.postValue(pokemons)
                    }

                }
        }
    }

    fun getPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemon(id)
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect {
                    pokeApiResponse = it

                    val pokemon = Pokemon(
                        pokeApiResponse.id,
                        pokeApiResponse.name,
                        pokeApiResponse.types.map { type ->
                            PokemonType(
                                type.type.name
                            )
                        }
                    )

                    _pokemonSuccess.postValue(pokemon)
                }
        }
    }
}

