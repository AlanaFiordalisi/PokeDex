package com.example.pokedex.repository

import com.example.pokedex.network.model.PokemonListResponse

interface PokemonRepository {

    suspend fun getPokemonList() : PokemonListResponse?
}
