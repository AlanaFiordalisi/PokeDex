package com.example.pokedex.screens.pokemonlist

import com.example.pokedex.model.PokemonListResponse

sealed interface PokemonListState {
    data object Loading : PokemonListState
    data class Loaded(
        val list: PokemonListResponse
    ) : PokemonListState
    data object Error : PokemonListState
}
