package com.example.pokedex.repository

import com.example.pokedex.network.model.PokemonDetailResponse
import com.example.pokedex.network.model.PokemonListResponse

interface PokemonRepository {

    suspend fun getPokemonList() : PokemonListResponse?

    suspend fun getPokemonDetail(name: String): PokemonDetailResponse?
}
