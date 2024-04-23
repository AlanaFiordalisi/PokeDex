package com.example.pokedex.screens.pokemondetail

import androidx.compose.ui.graphics.Color

enum class PokemonTypes(
    val label: String,
    val surfaceLight: Color,
    val onSurfaceLight: Color,
    val surfaceDark: Color,
    val onSurfaceDark: Color,
) {
//    GRASS(
//        label = "grass",
//        surfaceLight = ,
//        onSurfaceLight = ,
//        surfaceDark = ,
//        onSurfaceDark = ,
//    ),
    WATER(
        label = "water",
        surfaceLight = Color(0xFF466363),
        onSurfaceLight = Color(0xFF283A3A),
        surfaceDark = Color(0xFF283A3A),
        onSurfaceDark = Color(0xFF466363),
    ),
}