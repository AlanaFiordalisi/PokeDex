package com.example.pokedex.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.network.model.PokemonDetailResponse
import com.example.pokedex.network.model.PokemonSpritesResponse
import com.example.pokedex.ui.theme.PokeDexTheme

@Composable
fun PokemonDetailRoute(
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val detail by viewModel.pokemonDetail.collectAsState()
    PokemonDetailScreen(
        detail = detail
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonDetailScreen(
    detail: PokemonDetailResponse?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Display 10 items
        val pagerState = rememberPagerState(pageCount = { 10 })
        HorizontalPager(
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(detail?.sprites?.backDefault)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(100.dp)
                        .align(Alignment.Center)
                )
            }
//            // Our page content
//            Text(
//                text = "Page: $page",
//                modifier = Modifier.fillMaxWidth()
//            )
        }
        detail?.let {
            Text("id: ${it.id}")
            Text("name: ${it.name}")
            Text("height: ${it.height}")
            Text("weight: ${it.weight}")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PokemonDetailPreview() {
    PokeDexTheme {
        PokemonDetailScreen(
            detail = PokemonDetailResponse(
                id = 35,
                name = "Clefairy",
                height = 3,
                weight = 2,
                sprites = PokemonSpritesResponse()
            )
        )
    }
}
