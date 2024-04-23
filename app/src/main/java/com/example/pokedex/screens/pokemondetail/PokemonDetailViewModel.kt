package com.example.pokedex.screens.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    val name: String? = savedStateHandle["name"]

    private val _detailState = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val detailState: StateFlow<PokemonDetailState> = _detailState

    init {
        if (name == null) {
            _detailState.value = PokemonDetailState.Error
        } else {
            getPokemonDetail()
        }
    }

    fun getPokemonDetail() {
        viewModelScope.launch {
            _detailState.emit(PokemonDetailState.Loading)
            name?.let {
                val detail = pokemonRepository.getPokemonDetail(it)
                if (detail != null) {
                    _detailState.emit(
                        PokemonDetailState.Loaded(detail)
                    )
                } else {
                    _detailState.emit(PokemonDetailState.Error)
                }
            }
        }
    }
}