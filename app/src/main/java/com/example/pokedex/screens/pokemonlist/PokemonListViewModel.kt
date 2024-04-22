package com.example.pokedex.screens.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _listState = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val listState: StateFlow<PokemonListState> = _listState

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            _listState.emit(PokemonListState.Loading)
            val list = pokemonRepository.getPokemonList()
            if (list != null) {
                _listState.emit(
                    PokemonListState.Loaded(list)
                )
            } else {
                _listState.emit(PokemonListState.Error)
            }
        }
    }
}