package com.frommetoyou.interchallenge.character_module.presentation

import android.widget.AbsListView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frommetoyou.interchallenge.core.entities.characters.CharacterResponse
import com.frommetoyou.interchallenge.core.entities.events.Events
import com.frommetoyou.interchallenge.core.entities.characters.Result
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.core.util.Constants.Companion.QUERY_PAGE_SIZE
import com.frommetoyou.interchallenge.core.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CharactersViewModel(
    val charactersRepository: CharactersRepository
) : ViewModel() {
    val characters: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val events: MutableLiveData<Resource<Events>> = MutableLiveData()
    val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    val character: MutableLiveData<Result> = MutableLiveData()
    var charactersResponse: CharacterResponse? = null
    var charactersPage = 1

    init {
        getCharacters()
        getEvents()
    }

    fun getCharacters(offset: Int = 0) = viewModelScope.launch {
        characters.postValue(Resource.Loading())
        val response = charactersRepository.getCharacters(offset)
        characters.postValue(handleCharactersResponse(response))
    }

    fun getEvents() = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = charactersRepository.getEvents()
        events.postValue(handleEventsResponse(response))
    }

    private fun handleCharactersResponse(response: Response<CharacterResponse>): Resource<CharacterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                charactersPage++
                if (charactersResponse == null) charactersResponse = resultResponse
                else {
                    val oldCharacters = charactersResponse?.data?.results
                    val newCharacters = resultResponse.data.results
                    oldCharacters?.addAll(newCharacters)
                }
                return Resource.Success(charactersResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleEventsResponse(response: Response<Events>): Resource<Events> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun setCharacterToDetail(character: Result) {
        this.character.value = character
    }

    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }
}