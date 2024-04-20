package com.example.pokedex.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.network.model.PokemonDetailResponse

@Composable
fun PokemonDetailRoute(
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val detail by viewModel.pokemonDetail.collectAsState()
    PokemonDetailScreen(
        detail = detail
    )
}

@Composable
fun PokemonDetailScreen(
    detail: PokemonDetailResponse?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        detail?.let {
            Text("id: ${it.id}")
            Text("name: ${it.name}")
            Text("height: ${it.height}")
            Text("weight: ${it.weight}")
        }
    }
}
