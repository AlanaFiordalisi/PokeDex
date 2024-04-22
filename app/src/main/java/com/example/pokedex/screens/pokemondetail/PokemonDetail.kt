package com.example.pokedex.screens.pokemondetail

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.common.ErrorIndicator
import com.example.pokedex.common.LoadingIndicator
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
    val detailState by viewModel.detailState.collectAsState()
    PokemonDetailScreen(
        detailState = detailState
    )
}

@Composable
fun PokemonDetailScreen(
    detailState: PokemonDetailState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (detailState) {
            PokemonDetailState.Loading -> LoadingIndicator()
            PokemonDetailState.Error -> ErrorIndicator()
            is PokemonDetailState.Loaded -> DetailContent(detailState.details)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContent(
    details: PokemonDetailResponse
) {
    with(details) {
        // sprites pager
        sprites.getMap().let { spritesMap ->
            val pagerState = rememberPagerState(pageCount = { spritesMap.size })
            Box {
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    val spriteName = spritesMap.keys.elementAt(page)
                    val spriteUrl = spritesMap.values.elementAt(page)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(spriteUrl)
                                .build(),
                            contentDescription = stringResource(id = spriteName),
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
                                text = stringResource(id = spriteName),
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

        // stats
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                StatsBlock(
                    label = stringResource(id = R.string.label_id),
                    stat = stringResource(id = R.string.body_pound_x, id),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                StatsBlock(
                    label = stringResource(id = R.string.label_weight),
                    stat = weight.toString(),
                    modifier = Modifier.weight(1f),
                    unit = stringResource(id = R.string.body_hectograms)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                StatsBlock(
                    label = pluralStringResource(
                        id = R.plurals.body_types,
                        count = types.size
                    ),
                    stat = types.toJoinedString(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                StatsBlock(
                    label = stringResource(id = R.string.label_height),
                    stat = height.toString(),
                    modifier = Modifier.weight(1f),
                    unit = stringResource(id = R.string.body_decimeters)
                )
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
            detailState = PokemonDetailState.Loaded(
                PokemonDetailResponse(
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
        )
    }
}
