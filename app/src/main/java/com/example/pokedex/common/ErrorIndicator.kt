package com.example.pokedex.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedex.ui.theme.PokeDexTheme

@Composable
fun ErrorIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = null
        )
        Text(
            text = "Uh oh! This information failed to load."
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ErrorIndicatorPreview() {
    PokeDexTheme {
        ErrorIndicator()
    }
}
