package com.frommetoyou.interchallenge.feature_marvel.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.MarvelCharacter
import com.frommetoyou.interchallenge.feature_marvel.data.model.events.Events
import com.frommetoyou.interchallenge.feature_marvel.data.model.events.MarvelEvent
import com.frommetoyou.interchallenge.feature_marvel.domain.usecases.MarvelUseCases
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Resource
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states.CharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val PAGE_SIZE = 15

@HiltViewModel
class CharactersViewModel @Inject constructor(
    val marvelUseCases: MarvelUseCases
) : ViewModel() {
    private val _state = mutableStateOf(CharacterState())
    val state: State<CharacterState> = _state
    val characters: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val events: MutableLiveData<Resource<Events>> = MutableLiveData()
    val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    val character: MutableLiveData<MarvelCharacter> = MutableLiveData()
    var charactersResponse: CharacterResponse? = null
    val charactersPage = mutableStateOf(1)
    private var charScrollPosition = 0

    init {
        getCharacters()
        getEvents()
    }

    private fun incrementCharsPage() {
        charactersPage.value = charactersPage.value + 1
    }

    fun onChangeCharsScrollPosition(position: Int) {
        charScrollPosition = position
    }

    fun nextCharsPage() = viewModelScope.launch {
        if ((charScrollPosition + 1) >= (charactersPage.value * PAGE_SIZE)) {
            incrementCharsPage()
            if (charactersPage.value > 1) {
                getCharacters(charactersPage.value * PAGE_SIZE)
            }
            delay(1000)

        }
    }

    fun showLoading(show: Boolean) {
        _state.value = _state.value.copy(
            showLoading = show
        )
    }

    fun getCharacters(offset: Int = 0) = viewModelScope.launch {
        showLoading(true)
        val getChars = flow<List<MarvelCharacter>> {
            val response = marvelUseCases.getCharacters(offset)
            emit(response.body()!!.data.results)
        }
            .flowOn(Dispatchers.IO)
        getChars.collect {
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    characterList = it,
                )
                showLoading(false)
            }
        }
    }

    fun getEvents() = viewModelScope.launch {
        showLoading(true)
        val getEvents = flow<List<MarvelEvent>> {
            val response = marvelUseCases.getEvents()
            response.body()?.let {
                it.data.results
                emit(it.data.results)
            }
        }
            .flowOn(Dispatchers.IO)
        getEvents.collect {
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    eventList = it,
                )
                showLoading(false)
            }
        }
    }

    fun setCharacterToDetail(character2: MarvelCharacter) = viewModelScope.launch {
        character.postValue(character2)
        _state.value = _state.value.copy(
            selectedCharacter = character2
        )
    }

    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }
}