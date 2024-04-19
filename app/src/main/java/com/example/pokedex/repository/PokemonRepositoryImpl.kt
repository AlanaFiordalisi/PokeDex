package com.example.pokedex.repository

import com.example.pokedex.network.PokeApiService
import com.example.pokedex.network.model.PokemonListResponse
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonList(): PokemonListResponse? {
        var response: PokemonListResponse? = null
        pokemonService.getPokemonList().onSuccess {
            response = this.data
        }
        return response
    }
}