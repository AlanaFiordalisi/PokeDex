package com.example.pokedex.network

import com.example.pokedex.model.PokemonDetailResponse
import com.example.pokedex.model.PokemonListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
    ) : ApiResponse<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ) : ApiResponse<PokemonDetailResponse>
}