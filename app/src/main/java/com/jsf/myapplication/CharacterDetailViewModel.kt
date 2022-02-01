package com.jsf.myapplication


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.jsf.myapplication.data.repositories.CharactersRepository
import com.jsf.myapplication.domain.model.Character
import com.jsf.myapplication.domain.model.CharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val _stateCharacterDetail: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.Success(emptyList()))
    val stateCharacterDetail: StateFlow<ViewState> = _stateCharacterDetail

    private val charactersRepositories: CharactersRepository = CharactersRepository()

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            try {
                _stateCharacterDetail.value =
                    ViewStateCharacterDetail.Success(charactersRepositories.getCharacterDetail(id))
            } catch (t: Throwable) {
                _stateCharacterDetail.value = ViewStateCharacterDetail.Error(t)
            }

        }
    }

}

sealed class ViewStateCharacterDetail {
    data class Success(val character: CharacterDetail) : ViewState()
    data class Error(val error: Throwable) : ViewState()
}