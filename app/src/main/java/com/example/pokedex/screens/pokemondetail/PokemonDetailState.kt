package com.example.pokedex.screens.pokemondetail

import com.example.pokedex.model.PokemonDetailResponse

sealed interface PokemonDetailState {
    data object Loading : PokemonDetailState
    data class Loaded(
        val details: PokemonDetailResponse
    ) : PokemonDetailState
    data object Error : PokemonDetailState
}
