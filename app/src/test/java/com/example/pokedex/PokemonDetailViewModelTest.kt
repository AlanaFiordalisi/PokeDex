package com.example.pokedex

import androidx.lifecycle.SavedStateHandle
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonSprites
import com.example.pokedex.model.PokemonType
import com.example.pokedex.model.PokemonTypeResponse
import com.example.pokedex.screens.pokemondetail.PokemonDetailState
import com.example.pokedex.screens.pokemondetail.PokemonDetailViewModel
import com.example.pokedex.test.FakePokemonRepository
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonDetailViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var pokemonRepository: FakePokemonRepository

    @Before
    fun setUp() {
        pokemonRepository = FakePokemonRepository()
    }

    private fun initViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle()
    ): PokemonDetailViewModel {
        return PokemonDetailViewModel(
            savedStateHandle = savedStateHandle,
            pokemonRepository = pokemonRepository
        )
    }

    @Test
    fun `getPokemonDetail sets state to Loaded on success`() = runTest {
        // arrange
        val savedStateHandle = SavedStateHandle().apply {
            set("pokemonName", "bulbasaur")
        }

        pokemonRepository.setPokemonDetail(bulbasaurDetail)

        // act
        val viewModel = initViewModel(savedStateHandle)

        // assert
        assertEquals(PokemonDetailState.Loading, viewModel.detailState.first())
        assertEquals(
            PokemonDetailState.Loaded(bulbasaurDetail),
            viewModel.detailState.drop(1).first()
        )
    }

    @Test
    fun `getPokemonDetail sets state to Error on failure`() = runTest {
        // arrange
        val savedStateHandle = SavedStateHandle().apply {
            set("pokemonName", "bulbasaur")
        }

        pokemonRepository.setPokemonDetail(null) // not strictly necessary to call this, but it's explicit and quick

        // act
        val viewModel = initViewModel(savedStateHandle)

        // assert
        assertEquals(PokemonDetailState.Loading, viewModel.detailState.first())
        assertEquals(PokemonDetailState.Error, viewModel.detailState.drop(1).first())
    }

    companion object FakeData {
        private val bulbasaurDetail = PokemonDetail(
            id = 1,
            name = "bulbasaur",
            height = 7,
            weight = 69,
            sprites = PokemonSprites(
                frontDefault = "fake_url",
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
    }
}
