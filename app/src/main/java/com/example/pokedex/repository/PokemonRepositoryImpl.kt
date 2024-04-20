package com.example.pokedex.repository

import com.example.pokedex.network.PokeApiService
import com.example.pokedex.network.model.PokemonDetailResponse
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

    override suspend fun getPokemonDetail(name: String): PokemonDetailResponse? {
        var response: PokemonDetailResponse? = null
        pokemonService.getPokemonDetail(name).onSuccess {
            response = this.data
        }
        return response
    }
}