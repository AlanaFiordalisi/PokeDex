package com.example.pokedex.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.ui.theme.PokeDexTheme

@Composable
fun ErrorIndicator(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.poliwag),
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.body_loading_failure)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = onTryAgainClick
        ) {
            Text(text = stringResource(R.string.label_try_again))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ErrorIndicatorPreview() {
    PokeDexTheme {
        ErrorIndicator(
            onTryAgainClick = {}
        )
    }
}
