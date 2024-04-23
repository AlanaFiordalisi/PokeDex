package com.example.pokedex.screens.pokemonlist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.common.ErrorIndicator
import com.example.pokedex.common.LoadingIndicator
import com.example.pokedex.model.PokemonListResponse
import com.example.pokedex.model.getPokemonNumber
import com.example.pokedex.model.getPokemonSpriteUrl
import com.example.pokedex.ui.theme.DarkGrey
import com.example.pokedex.ui.theme.LightGrey
import com.example.pokedex.ui.theme.PokeDexTheme

@Composable
fun PokemonListRoute(
    onPokemonClick: (String) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val listState by viewModel.listState.collectAsState() // TODO: with lifecycle
    PokemonListScreen(
        listState = listState,
        onPokemonClick = onPokemonClick,
        onTryAgainClick = viewModel::getPokemonList,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListScreen(
    listState: PokemonListState,
    onPokemonClick: (String) -> Unit,
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_pokemon_list))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (listState) {
                PokemonListState.Loading -> LoadingIndicator()
                PokemonListState.Error -> ErrorIndicator(onTryAgainClick)
                is PokemonListState.Loaded -> ListContent(
                    pokemonList = listState.list,
                    onPokemonClick = onPokemonClick
                )
            }
        }
    }
}

@Composable
private fun ListContent(
    pokemonList: PokemonListResponse,
    onPokemonClick: (String) -> Unit
) {
    val localConfiguration = LocalConfiguration.current
    when (localConfiguration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.size(10.dp))
                }
                items(pokemonList.results) { pokemon ->
                    PokemonItemRow(
                        name = pokemon.name,
                        number = pokemon.getPokemonNumber() ?: 0,
                        imageUrl = pokemon.getPokemonSpriteUrl(),
                        onClick = { onPokemonClick(pokemon.name) },
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }

        else -> {
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
                items(pokemonList.results) { pokemon ->
                    PokemonItemSquare(
                        name = pokemon.name,
                        number = pokemon.getPokemonNumber() ?: 0,
                        imageUrl = pokemon.getPokemonSpriteUrl(),
                        onClick = { onPokemonClick(pokemon.name) },
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonItemRow(
    name: String,
    number: Int,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build(),
                contentDescription = name,
                modifier = Modifier
                    .size(48.dp)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name)
                PokemonNumberChip(label = number.toString())
            }
        }
    }
}

@Composable
private fun PokemonItemSquare(
    name: String,
    number: Int,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .widthIn(min = 150.dp)
                .padding(4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build(),
                contentDescription = name,
                modifier = Modifier
                    .size(64.dp)
            )
            PokemonNumberChip(label = number.toString())
            Text(text = name)
        }
    }
}

@Composable
private fun PokemonNumberChip(label: String) {
    val chipColor = if (isSystemInDarkTheme()) DarkGrey else LightGrey
    Box(
        modifier = Modifier
            .widthIn(min = 36.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(chipColor)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 4.dp, horizontal = 6.dp)
        )
    }
}

@Composable
@Preview
private fun PokemonItemPreview() {
    PokeDexTheme {
        PokemonItemRow(
            name = "Pikachu",
            number = 987,
            onClick = {},
            imageUrl = ""
        )
    }
}

@Composable
@Preview
private fun PokemonItemSquarePreview() {
    PokeDexTheme {
        PokemonItemSquare(
            name = "Pikachu",
            number = 987,
            onClick = {},
            imageUrl = ""
        )
    }
}
