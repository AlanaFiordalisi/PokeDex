package com.example.pokedex.test

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.example.pokedex.repository.PokemonRepository
import javax.inject.Inject

class FakePokemonRepository @Inject constructor() : PokemonRepository {

    private var pokemonList: PokemonList? = null
    private var pokemonDetail: PokemonDetail? = null

    fun setPokemonList(list: PokemonList?) {
        this.pokemonList = list
    }

    fun setPokemonDetail(detail: PokemonDetail?) {
        this.pokemonDetail = detail
    }

    override suspend fun getPokemonList(limit: Int): Result<PokemonList> {
        pokemonList?.let {
            return Result.success(it)
        } ?: return Result.failure(Exception())
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        pokemonDetail?.let {
            return Result.success(it)
        } ?: return Result.failure(Exception())
    }
}
