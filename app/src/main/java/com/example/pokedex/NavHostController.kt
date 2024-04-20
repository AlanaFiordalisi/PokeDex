package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex.screens.PokemonDetailRoute
import com.example.pokedex.screens.PokemonListRoute

@Composable
fun PokeDexNavHost(
    navHostController: NavHostController
) {
    NavHost(navHostController, startDestination = "pokemon_list") {
        composable(
            route = "pokemon_list"
        ) {
            PokemonListRoute(
                onPokemonClick = {
                    navHostController.navigate("pokemon_detail")
                }
            )
        }

        composable(
            route = "pokemon_detail"
        ) {
            PokemonDetailRoute()
        }
    }
}
