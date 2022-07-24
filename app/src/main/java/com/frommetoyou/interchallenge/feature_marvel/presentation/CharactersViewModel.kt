package com.frommetoyou.interchallenge.feature_marvel.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.Result
import com.frommetoyou.interchallenge.feature_marvel.domain.model.events.Events
import com.frommetoyou.interchallenge.feature_marvel.data.repository.CharactersRepositoryImpl
import com.frommetoyou.interchallenge.feature_marvel.domain.use_case.GetCharacters
import com.frommetoyou.interchallenge.feature_marvel.domain.use_case.MarvelUseCases
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val marvelUseCases: MarvelUseCases
) : ViewModel() {
    val characters: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val events: MutableLiveData<Resource<Events>> = MutableLiveData()
    val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    val character: MutableLiveData<Result> = MutableLiveData()
    var charactersResponse: CharacterResponse? = null
    var charactersPage: Int = 1
    private var getCharactersJob: Job? = null

    init {
        getCharacters()
        getEvents()
    }

    fun getCharacters(offset: Int = 0) {
        characters.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = marvelUseCases.getCharacters(offset)
            characters.postValue(handleCharactersResponse(response))
        }
    }

    fun getEvents() = viewModelScope.launch(Dispatchers.IO) {
        events.postValue(Resource.Loading() )
        val response = marvelUseCases.getEvents()
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

    fun setCharacterToDetail(character2: Result) = viewModelScope.launch {
        character.postValue(character2)
    }

    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }
}