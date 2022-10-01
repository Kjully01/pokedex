package br.com.pokedex.data_remote.repository

import br.com.pokedex.data_remote.api.Api
import br.com.pokedex.data_remote.model.PokemonListResponse
import br.com.pokedex.data_remote.utils.ApiConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PokemonRepositoryRemote {

    private var pokemonRoute = Api(ApiConstants.BASE_URL).create()

    suspend fun getPokemons(): Flow<PokemonListResponse> {
        return flow {
            pokemonRoute.getPokemons(20)
                .let { response ->
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        throw HttpException(response)
                    }
                }?.let {
                    emit(it)
                }
        }
    }

}