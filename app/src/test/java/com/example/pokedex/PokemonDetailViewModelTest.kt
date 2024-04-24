package com.example.pokedex

import androidx.lifecycle.SavedStateHandle
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonSprites
import com.example.pokedex.model.PokemonType
import com.example.pokedex.model.PokemonTypeResponse
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.screens.pokemondetail.PokemonDetailState
import com.example.pokedex.screens.pokemondetail.PokemonDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
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

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setUp() {
        pokemonRepository = mockk(relaxed = true)
    }

    private fun initViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()) {
        viewModel = PokemonDetailViewModel(
            savedStateHandle = savedStateHandle,
            pokemonRepository = pokemonRepository
        )
    }

    @Test
    fun `state is set to Error if nav arg is null`() = runTest {
        // arrange
        val savedStateHandle = SavedStateHandle().apply {
            set("name", null)
        }

        // act
        initViewModel(savedStateHandle)

        // assert
        assertEquals(PokemonDetailState.Error, viewModel.detailState.value)
    }

    @Test
    fun `getPokemonDetail sets state to Loaded on success`() = runTest {
        // arrange
        val savedStateHandle = SavedStateHandle().apply {
            set("name", "bulbasaur")
        }

        coEvery {
            pokemonRepository.getPokemonDetail("bulbasaur")
        } returns bulbasaurDetail

        // act
        initViewModel(savedStateHandle)

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
            set("name", "bulbasaur")
        }

        coEvery { pokemonRepository.getPokemonDetail("bulbasaur") } returns null

        // act
        initViewModel(savedStateHandle)

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
