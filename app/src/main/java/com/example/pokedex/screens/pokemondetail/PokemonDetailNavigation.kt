package com.example.pokedex.screens.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val POKEMON_NAME_ARG = "pokemonName"
private const val POKEMON_DETAIL_ROUTE_BASE = "pokemon_detail"
private const val POKEMON_DETAIL_ROUTE = "$POKEMON_DETAIL_ROUTE_BASE?$POKEMON_NAME_ARG={$POKEMON_NAME_ARG}"

internal class PokemonDetailArgs(
    val pokemonName: String
) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(checkNotNull(savedStateHandle[POKEMON_NAME_ARG]) as String)
}

fun NavController.navigateToPokemonDetail(
    pokemonName: String
) {
    navigate(
        route = "$POKEMON_DETAIL_ROUTE_BASE?$POKEMON_NAME_ARG=$pokemonName"
    )
}

fun NavGraphBuilder.pokemonDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = POKEMON_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(POKEMON_NAME_ARG) {
                nullable = false
                type = NavType.StringType
            },
        ),
    ) {
        PokemonDetailRoute(
            onBackClick = onBackClick
        )
    }
}
