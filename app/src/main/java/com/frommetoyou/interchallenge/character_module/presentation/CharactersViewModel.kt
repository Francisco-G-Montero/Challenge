package com.frommetoyou.interchallenge.character_module.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.interchallenge.core.entities.characters.CharacterResponse
import com.frommetoyou.interchallenge.core.entities.events.Events
import com.frommetoyou.interchallenge.core.entities.characters.Result
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.core.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CharactersViewModel(
    val charactersRepository: CharactersRepository
) : ViewModel() {
    val characters : MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val events : MutableLiveData<Resource<Events>> = MutableLiveData()
    val character : MutableLiveData<Result> = MutableLiveData()
    val charactersPage = 1

    init {
        getCharacters()
        getEvents()
    }
    fun getCharacters() = viewModelScope.launch {
        characters.postValue(Resource.Loading())
        val response = charactersRepository.getCharacters()
        characters.postValue(handleCharactersResponse(response))
    }
    fun getEvents() = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = charactersRepository.getEvents()
        events.postValue(handleEventsResponse(response))
    }

    private fun handleCharactersResponse(response: Response<CharacterResponse>): Resource<CharacterResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleEventsResponse(response: Response<Events>): Resource<Events>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun setCharacterToDetail(character: Result){
        this.character.value = character
    }
}