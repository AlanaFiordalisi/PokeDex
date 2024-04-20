package com.example.pokedex.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.model.PokemonDetailResponse
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

    private val _pokemonDetail = MutableStateFlow<PokemonDetailResponse?>(null)
    val pokemonDetail: StateFlow<PokemonDetailResponse?> = _pokemonDetail

    init {
        // TODO: if null name, error state

        getPokemonDetail()
    }

    private fun getPokemonDetail() {
        viewModelScope.launch {
            name?.let {
                val detail = pokemonRepository.getPokemonDetail(it)
                // TODO: if null detail, error state
                _pokemonDetail.emit(detail)
            }
        }
    }
}