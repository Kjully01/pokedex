package br.com.pokedex.data_remote.api

import br.com.pokedex.data_remote.model.PokemonApiResponse
import br.com.pokedex.data_remote.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutesApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): Response<PokemonApiResponse>
}