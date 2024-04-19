package com.example.pokedex.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.network.model.PokemonListResponse

@Composable
fun PokemonListRoute(
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState() // TODO: with lifecycle
    PokemonListScreen(
        pokemonList = pokemonList
    )
}

@Composable
fun PokemonListScreen(
    pokemonList: PokemonListResponse?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("Pokemon List")
        if (pokemonList != null) {
            LazyColumn {
                items(pokemonList.results) {
                    Text(it.name)
                }
            }
        } else {
            Text("Uh oh! No Pokemon :(")
        }
    }
}
