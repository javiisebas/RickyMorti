package com.jsf.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsf.myapplication.data.repositories.CharactersRepository
import com.jsf.myapplication.data.repositories.EpisodeRepository
import com.jsf.myapplication.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val _state: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.Success(emptyList()))
    val state: StateFlow<ViewState> = _state

    private val charactersRepository: CharactersRepository = CharactersRepository()
    private val episodeRepository: EpisodeRepository = EpisodeRepository()

    // Damos valor al state flow dentro de una corrutina
    fun getCharacters() {
        viewModelScope.launch {
            try {
                val charactersResult = charactersRepository.getCharacters()
                val newCharactersList = charactersResult.map { character ->
                    val episodeResult = episodeRepository.getEpisodeName(character.episode)
                    character.episodeName = episodeResult.name
                    character
                }
                _state.value = ViewState.Success(newCharactersList)
            } catch (t: Throwable) {
                _state.value = ViewState.Error(t)
            }
        }
    }
}

sealed class ViewState {
    data class Success(val characters: List<Character>) : ViewState()
    data class Error(val error: Throwable) : ViewState()
}