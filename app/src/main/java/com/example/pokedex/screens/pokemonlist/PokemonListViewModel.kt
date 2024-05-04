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

    private val _listState = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val listState: StateFlow<PokemonListState> = _listState

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            _listState.emit(PokemonListState.Loading)
            pokemonRepository.getPokemonList()
                .onSuccess { list ->
                    _listState.emit(
                        PokemonListState.Loaded(list)
                    )
                }.onFailure {
                    _listState.emit(PokemonListState.Error)
                }
        }
    }
}