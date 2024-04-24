package com.example.pokedex.repository

import com.example.pokedex.network.PokeApiService
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int): PokemonList? {
        var response: PokemonList? = null
        pokemonService.getPokemonList(limit).onSuccess {
            response = this.data
        }
        return response
    }

    override suspend fun getPokemonDetail(name: String): PokemonDetail? {
        var response: PokemonDetail? = null
        pokemonService.getPokemonDetail(name).onSuccess {
            response = this.data
        }
        return response
    }
}