package com.example.pokedex.network

import com.example.pokedex.network.model.PokemonListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList() : ApiResponse<PokemonListResponse>
}