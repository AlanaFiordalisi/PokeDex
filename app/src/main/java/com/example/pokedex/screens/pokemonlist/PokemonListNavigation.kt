package com.example.pokedex.screens.pokemonlist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val POKEMON_LIST_ROUTE = "pokemon_list"

fun NavGraphBuilder.pokemonListScreen(
    onPokemonClick: (String) -> Unit
) {
    composable(
        route = POKEMON_LIST_ROUTE,
    ) {
        PokemonListRoute(
            onPokemonClick = onPokemonClick
        )
    }
}
