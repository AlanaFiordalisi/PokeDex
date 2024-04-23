package com.example.pokedex.screens.pokemondetail

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.pokedex.ui.theme.Green900
import com.example.pokedex.ui.theme.LightGrey
import com.example.pokedex.ui.theme.PokeDexTheme
import com.example.pokedex.ui.theme.White

@Composable
fun PokemonDetailRoute(
    onBackClick: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val detailState by viewModel.detailState.collectAsState()
    PokemonDetailScreen(
        name = viewModel.name,
        detailState = detailState,
        onBackClick = onBackClick,
        onTryAgainClick = viewModel::getPokemonDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailScreen(
    name: String?,
    detailState: PokemonDetailState,
    onBackClick: () -> Unit,
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name?.replaceFirstChar(Char::uppercaseChar) ?: "")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            when (detailState) {
                PokemonDetailState.Loading -> LoadingIndicator()
                PokemonDetailState.Error -> ErrorIndicator(onTryAgainClick)
                is PokemonDetailState.Loaded -> DetailContent(detailState.details)
            }
        }
    }
}

@Composable
private fun DetailContent(
    details: PokemonDetailResponse,
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column {
                SpritesPager(details = details)
                Spacer(modifier = Modifier.size(8.dp))
                PokemonStats(details = details)
            }
        }

        else -> {
            Row {
                SpritesPager(
                    details = details,
                    modifier = Modifier.weight(1f)
                )
                PokemonStats(
                    details = details,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SpritesPager(
    details: PokemonDetailResponse,
    modifier: Modifier = Modifier
) {
    with(details) {
        // sprites pager
        sprites.getMap().let { spritesMap ->
            val pagerState = rememberPagerState(pageCount = { spritesMap.size })
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
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
                PagerProgressIndicator(
                    pageCount = spritesMap.size,
                    currentPage = pagerState.currentPage,
                )
            }
        }
    }
}

@Composable
private fun PagerProgressIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        repeat(pageCount) { index ->
            val width by animateDpAsState(
                if (currentPage == index) 24.dp else 12.dp,
                label = "indicator width"
            )
            Box(
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .height(12.dp)
                    .width(width)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(LightGrey)
            )
        }
    }
}

@Composable
private fun PokemonStats(
    details: PokemonDetailResponse,
    modifier: Modifier = Modifier,
) {
    with(details) {
        Column(
            modifier = modifier
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
private fun StatsBlock(
    label: String,
    stat: String,
    modifier: Modifier = Modifier,
    unit: String? = null,
) {
    val backgroundColor = if (isSystemInDarkTheme()) Green900 else Color(0xFFB8E3AF)
    val fontColor = if (isSystemInDarkTheme()) Color(0xFFB8E3AF) else Green900
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(label)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stat,
            fontSize = 36.sp,
            color = fontColor,
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
private fun PokemonDetailPreview() {
    PokeDexTheme {
        PokemonDetailScreen(
            name = "Clefairy",
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
            ),
            onBackClick = {},
            onTryAgainClick = {}
        )
    }
}
