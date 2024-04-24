package com.example.pokedex.repository

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int = 60): PokemonList?

    suspend fun getPokemonDetail(name: String): PokemonDetail?
}
