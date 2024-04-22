package com.example.pokedex.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.ui.theme.PokeDexTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            label = "pokeball rotation",
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
        )
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "loading",
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = angle
                }
        )
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    PokeDexTheme {
        LoadingIndicator()
    }
}
