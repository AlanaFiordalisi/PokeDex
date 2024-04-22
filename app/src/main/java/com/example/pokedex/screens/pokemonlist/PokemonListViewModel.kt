package com.example.pokedex.screens.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.model.PokemonListResponse
import com.example.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    private var _pokemonList = MutableStateFlow<PokemonListResponse?>(null)
    val pokemonList: StateFlow<PokemonListResponse?> = _pokemonList

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            val list = pokemonRepository.getPokemonList()
            _pokemonList.emit(list)
        }
    }
}