package com.example.pokedex

import com.example.pokedex.network.model.PokemonListResponse
import com.example.pokedex.network.model.PokemonListResult
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.screens.pokemonlist.PokemonListState
import com.example.pokedex.screens.pokemonlist.PokemonListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PokemonListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setUp() {
        pokemonRepository = mockk(relaxed = true)
    }

    @Test
    fun `getPokemonList sets state to Loaded on success`() = runTest {
        // arrange
        coEvery { pokemonRepository.getPokemonList() } returns listResponse

        // act
        val viewModel = PokemonListViewModel(pokemonRepository)

        // assert
        assertEquals(PokemonListState.Loading, viewModel.listState.first())
        assertEquals(
            PokemonListState.Loaded(listResponse),
            viewModel.listState.drop(1).first()
        )
    }

    @Test
    fun `getPokemonList sets state to Error on failure`() = runTest {
        // arrange
        coEvery { pokemonRepository.getPokemonList() } returns null

        // act
        val viewModel = PokemonListViewModel(pokemonRepository)

        // assert
        assertEquals(PokemonListState.Loading, viewModel.listState.first())
        assertEquals(PokemonListState.Error, viewModel.listState.drop(1).first())
    }

    companion object FakeData {
        private val listResponse = PokemonListResponse(
            results = listOf(
                PokemonListResult(
                    name = "bulbasaur",
                    url = "fake_url",
                ),
                PokemonListResult(
                    name = "wartortle",
                    url = "fake_url",
                ),
                PokemonListResult(
                    name = "blastoise",
                    url = "fake_url"
                )
            )
        )
    }
}
