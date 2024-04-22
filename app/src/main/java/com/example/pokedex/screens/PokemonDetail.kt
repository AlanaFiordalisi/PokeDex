package com.example.pokedex.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.network.model.PokemonDetailResponse
import com.example.pokedex.network.model.PokemonSpritesResponse
import com.example.pokedex.network.model.PokemonType
import com.example.pokedex.network.model.PokemonTypeResponse
import com.example.pokedex.network.model.getMap
import com.example.pokedex.network.model.toJoinedString
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
        Spacer(modifier = Modifier.size(8.dp))
        detail?.let {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                ) {
                    StatsBlock(
                        label = "ID",
                        stat = "#${it.id}",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    StatsBlock(
                        label = "Weight",
                        stat = it.weight.toString(),
                        modifier = Modifier.weight(1f),
                        unit = "hectograms"
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                ) {
                    StatsBlock(
                        label = if (it.types.size == 1) "Type" else "Types",
                        stat = it.types.toJoinedString(),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    StatsBlock(
                        label = "Height",
                        stat = it.height.toString(),
                        modifier = Modifier.weight(1f),
                        unit = "decimeters"
                    )
                }
            }
        }
    }
}

@Composable
fun StatsBlock(
    label: String,
    stat: String,
    modifier: Modifier = Modifier,
    unit: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFB8E3AF))
            .padding(8.dp)
    ) {
        Text(label)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stat,
            fontSize = 36.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
        unit?.let {
            Text(
                text = unit,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
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
                ),
                types = listOf(
                    PokemonTypeResponse(
                        slot = 1,
                        type = PokemonType(
                            name = "grass"
                        )
                    ),
                )
            )
        )
    }
}
