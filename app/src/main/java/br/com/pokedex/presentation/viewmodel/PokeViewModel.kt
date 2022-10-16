package br.com.pokedex.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.data_remote.model.PokemonListResponse
import br.com.pokedex.data_remote.model.PokemonResponse
import br.com.pokedex.data_remote.repository.PokemonRepositoryRemote
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.PokemonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeViewModel : ViewModel() {

    private val repository: PokemonRepositoryRemote = PokemonRepositoryRemote()

    private val _pokeSuccess: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokeSuccess: LiveData<List<Pokemon>> = _pokeSuccess

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

                        _pokeSuccess.postValue(pokemons)
                    }

                }
        }
    }
}

