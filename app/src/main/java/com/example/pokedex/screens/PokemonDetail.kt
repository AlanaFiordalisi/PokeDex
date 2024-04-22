package com.example.pokedex.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.network.model.PokemonDetailResponse
import com.example.pokedex.network.model.PokemonSpritesResponse
import com.example.pokedex.network.model.getMap
import com.example.pokedex.ui.theme.PokeDexTheme
import com.example.pokedex.ui.theme.White

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
        detail?.sprites?.getMap()?.let { spritesMap ->
            val pagerState = rememberPagerState(pageCount = { spritesMap.size })
            Box {
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    val imageName = spritesMap.keys.elementAt(page)
                    val imageUrl = spritesMap.values.elementAt(page)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.Center)
                        )
                        Row(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0x99000000))
                                .align(Alignment.BottomEnd)
                        ) {
                            Text(
                                text = imageName,
                                color = White,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                            )
                        }
                    }
                }
            }
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
                sprites = PokemonSpritesResponse(
                    frontDefault = "fake_url"
                )
            )
        )
    }
}
