package com.example.pokedex.network

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
    ) : ApiResponse<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ) : ApiResponse<PokemonDetail>
}