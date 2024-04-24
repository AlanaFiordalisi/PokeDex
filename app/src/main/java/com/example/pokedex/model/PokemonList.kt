package com.example.pokedex.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonList(
    val results: List<PokemonListItem>
)

@JsonClass(generateAdapter = true)
data class PokemonListItem(
    val name: String,
    val url: String,
)

fun PokemonListItem.getPokemonNumber(): Int? {
    val regex = "/[0-9]*/$".toRegex()

    return regex.find(this.url)?.value?.trim('/')?.toInt()
}

fun PokemonListItem.getPokemonSpriteUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
            this.getPokemonNumber() +
            ".png"
}
