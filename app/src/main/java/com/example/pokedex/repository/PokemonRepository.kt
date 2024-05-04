package com.example.pokedex.repository

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList

interface PokemonRepository {

    /**
     * Get list of Pokemon names and sprite urls.
     *
     * @param limit – customize the number of items returned by the API. This
     * app only ever loads 60 items at a time.
     */
    suspend fun getPokemonList(limit: Int = 60): Result<PokemonList>

    /**
     * Get details about a specific Pokemon, by name,
     *
     * @param name – the name of the Pokemon that you want to get details for. Same
     * as the `name` in the PokemonList object.
     */
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}
