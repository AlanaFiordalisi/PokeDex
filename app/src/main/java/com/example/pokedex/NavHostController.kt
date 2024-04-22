package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex.screens.pokemondetail.PokemonDetailRoute
import com.example.pokedex.screens.pokemonlist.PokemonListRoute

@Composable
fun PokeDexNavHost(
    navHostController: NavHostController
) {
    NavHost(navHostController, startDestination = "pokemon_list") {
        composable(
            route = "pokemon_list"
        ) {
            PokemonListRoute(
                onPokemonClick = { name ->
                    navHostController.navigate("pokemon_detail/$name")
                }
            )
        }

        composable(
            route = "pokemon_detail/{name}"
        ) {
            PokemonDetailRoute()
        }
    }
}
