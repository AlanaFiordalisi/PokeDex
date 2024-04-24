package com.example.pokedex.screens.pokemondetail

import com.example.pokedex.model.PokemonDetail

sealed interface PokemonDetailState {
    data object Loading : PokemonDetailState
    data class Loaded(
        val details: PokemonDetail
    ) : PokemonDetailState
    data object Error : PokemonDetailState
}
