package com.example.pokedex.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    val results: List<PokemonListResult>
)

@JsonClass(generateAdapter = true)
data class PokemonListResult(
    val name: String,
    val url: String,
)

fun PokemonListResult.getPokemonNumber(): Int? {
    val regex = "/[0-9]*/$".toRegex()

    return regex.find(this.url)?.value?.trim('/')?.toInt()
}

fun PokemonListResult.getPokemonSpriteUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
            this.getPokemonNumber() +
            ".png"
}
