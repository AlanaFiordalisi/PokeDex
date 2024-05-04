package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pokedex.screens.pokemondetail.navigateToPokemonDetail
import com.example.pokedex.screens.pokemondetail.pokemonDetailScreen
import com.example.pokedex.screens.pokemonlist.POKEMON_LIST_ROUTE
import com.example.pokedex.screens.pokemonlist.pokemonListScreen

@Composable
fun PokeDexNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = POKEMON_LIST_ROUTE,
    ) {
        pokemonListScreen(
            onPokemonClick = navHostController::navigateToPokemonDetail
        )

        pokemonDetailScreen(
            onBackClick = navHostController::popBackStack
        )
    }
}
