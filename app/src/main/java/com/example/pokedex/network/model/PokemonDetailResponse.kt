package com.example.pokedex.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSpritesResponse
)

@JsonClass(generateAdapter = true)
data class PokemonSpritesResponse(
    @Json(name = "front_default")
    val frontDefault: String? = null,
    @Json(name = "front_shiny")
    val frontShiny: String? = null,
    @Json(name = "front_female")
    val frontFemale: String? = null,
    @Json(name = "front_shiny_female")
    val frontShinyFemale: String? = null,
    @Json(name = "back_default")
    val backDefault: String? = null,
    @Json(name = "back_shiny")
    val backShiny: String? = null,
    @Json(name = "back_female")
    val backFemale: String? = null,
    @Json(name = "back_shiny_female")
    val backShinyFemale: String? = null,
)
