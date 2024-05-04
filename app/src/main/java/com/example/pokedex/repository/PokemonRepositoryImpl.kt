package com.example.pokedex.repository

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.example.pokedex.network.PokeApiService
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int): Result<PokemonList> {
        var result: Result<PokemonList> = Result.failure(Exception())
        pokemonService.getPokemonList(limit).onSuccess {
            result = Result.success(this.data)
        }.onException {
            result = Result.failure(this.throwable)
        }
        return result
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        var result: Result<PokemonDetail> = Result.failure(Exception())
        pokemonService.getPokemonDetail(name).onSuccess {
            result = Result.success(this.data)
        }.onException {
            result = Result.failure(this.throwable)
        }
        return result
    }
}