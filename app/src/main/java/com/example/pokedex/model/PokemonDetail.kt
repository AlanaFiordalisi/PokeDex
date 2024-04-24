package com.example.pokedex.model

import com.example.pokedex.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeResponse>
)

@JsonClass(generateAdapter = true)
data class PokemonSprites(
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

fun PokemonSprites.getMap(): Map<Int, String> {
    val map = mutableMapOf<Int, String>()
    frontDefault?.let { map.put(R.string.label_front_default, it) }
    frontShiny?.let { map.put(R.string.label_front_shiny, it) }
    frontFemale?.let { map.put(R.string.label_front_female, it) }
    frontShinyFemale?.let { map.put(R.string.label_front_shiny_female, it) }
    backDefault?.let { map.put(R.string.label_back_default, it) }
    backShiny?.let { map.put(R.string.label_back_shiny, it) }
    backFemale?.let { map.put(R.string.label_back_female, it) }
    backShinyFemale?.let { map.put(R.string.label_back_shiny_female, it) }

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
