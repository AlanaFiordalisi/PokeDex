package com.example.pokedex.network

import com.example.pokedex.network.model.PokemonDetailResponse
import com.example.pokedex.network.model.PokemonListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList() : ApiResponse<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ) : ApiResponse<PokemonDetailResponse>
}