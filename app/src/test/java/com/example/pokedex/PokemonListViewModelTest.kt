package com.example.pokedex

import com.example.pokedex.model.PokemonList
import com.example.pokedex.model.PokemonListItem
import com.example.pokedex.screens.pokemonlist.PokemonListState
import com.example.pokedex.screens.pokemonlist.PokemonListViewModel
import com.example.pokedex.test.FakePokemonRepository
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

    private lateinit var pokemonRepository: FakePokemonRepository

    @Before
    fun setUp() {
        pokemonRepository = FakePokemonRepository()
    }

    @Test
    fun `getPokemonList sets state to Loaded on success`() = runTest {
        // arrange
        pokemonRepository.setPokemonList(fakePokemonList)

        // act
        val viewModel = PokemonListViewModel(pokemonRepository)

        // assert
        assertEquals(PokemonListState.Loading, viewModel.listState.first())
        assertEquals(
            PokemonListState.Loaded(fakePokemonList),
            viewModel.listState.drop(1).first()
        )
    }

    @Test
    fun `getPokemonList sets state to Error on failure`() = runTest {
        // arrange
        pokemonRepository.setPokemonList(null) // not strictly necessary to call this, but it's explicit and quick

        // act
        val viewModel = PokemonListViewModel(pokemonRepository)

        // assert
        assertEquals(PokemonListState.Loading, viewModel.listState.first())
        assertEquals(PokemonListState.Error, viewModel.listState.drop(1).first())
    }

    companion object FakeData {
        private val fakePokemonList = PokemonList(
            results = listOf(
                PokemonListItem(
                    name = "bulbasaur",
                    url = "fake_url",
                ),
                PokemonListItem(
                    name = "wartortle",
                    url = "fake_url",
                ),
                PokemonListItem(
                    name = "blastoise",
                    url = "fake_url"
                ),
            )
        )
    }
}
