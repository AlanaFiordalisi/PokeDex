package com.example.pokedex.screens.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.network.model.PokemonListResponse
import com.example.pokedex.network.model.getPokemonNumber
import com.example.pokedex.ui.theme.LightGrey
import com.example.pokedex.ui.theme.PokeDexTheme
import com.example.pokedex.ui.theme.White

@Composable
fun PokemonListRoute(
    onPokemonClick: (String) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState() // TODO: with lifecycle
    PokemonListScreen(
        pokemonList = pokemonList,
        onPokemonClick = onPokemonClick
    )
}

@Composable
fun PokemonListScreen(
    pokemonList: PokemonListResponse?,
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Text("Pokemon List")
        if (pokemonList != null) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                items(pokemonList.results) { pokemon ->
                    PokemonItem(
                        name = pokemon.name,
                        number = pokemon.getPokemonNumber() ?: 0,
                        onClick = { onPokemonClick(pokemon.name) },
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        } else {
            Text("Uh oh! No Pokemon :(")
        }
    }
}

@Composable
fun PokemonItem(
    name: String,
    number: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(LightGrey)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name)
                Box(
                    modifier = Modifier
                        .widthIn(min = 36.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(LightGrey)
                ) {
                    Text(
                        text = "$number",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PokemonItemPreview() {
    PokeDexTheme {
        PokemonItem(
            name = "Pikachu",
            number = 987,
            onClick = {},
        )
    }
}

