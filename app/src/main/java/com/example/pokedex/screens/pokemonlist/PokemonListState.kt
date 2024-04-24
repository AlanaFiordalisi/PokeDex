package com.example.pokedex.screens.pokemonlist

import com.example.pokedex.model.PokemonList

sealed interface PokemonListState {
    data object Loading : PokemonListState
    data class Loaded(
        val list: PokemonList
    ) : PokemonListState
    data object Error : PokemonListState
}
