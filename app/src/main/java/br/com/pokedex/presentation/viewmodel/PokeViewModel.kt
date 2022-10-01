package br.com.pokedex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data_remote.model.PokemonListResponse
import br.com.pokedex.data_remote.repository.PokemonRepositoryRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {

    private val repository: PokemonRepositoryRemote = PokemonRepositoryRemote()

//    private val _pokeListViewState = MutableLiveData<ViewState<List<PokemonListResponse>>>()
//    val pokeListViewState = _pokeListViewState as LiveData<ViewState<List<PokemonListResponse>>>

    private val _pokeSuccess: MutableLiveData<PokemonListResponse> = MutableLiveData()
    val pokeSuccess: LiveData<PokemonListResponse> = _pokeSuccess

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemons()
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect{
                    _pokeSuccess.postValue(it)
                }
        }
    }

}