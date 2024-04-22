package com.example.pokedex.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSpritesResponse,
    val types: List<PokemonTypeResponse>
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

fun PokemonSpritesResponse.getMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    frontDefault?.let { map.put("front default", it) }
    frontShiny?.let { map.put("front shiny", it) }
    frontFemale?.let { map.put("front female", it) }
    frontShinyFemale?.let { map.put("front shiny female", it) }
    backDefault?.let { map.put("back default", it) }
    backShiny?.let { map.put("back shiny", it) }
    backFemale?.let { map.put("back female", it) }
    backShinyFemale?.let { map.put("back shiny female", it) }

    return map
}

@JsonClass(generateAdapter = true)
data class PokemonTypeResponse(
    val slot: Int,
    val type: PokemonType
)

@JsonClass(generateAdapter = true)
data class PokemonType(
    val name: String
)

fun List<PokemonTypeResponse>.toJoinedString(): String {
    var string = ""
    this.forEachIndexed { index, pokemonType ->
        string += pokemonType.type.name
        if (index < this.size - 1) {
            string += ", "
        }
    }
    return string
}
