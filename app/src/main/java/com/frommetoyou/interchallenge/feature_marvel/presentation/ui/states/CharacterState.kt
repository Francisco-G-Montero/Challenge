package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states

import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.MarvelCharacter
import com.frommetoyou.interchallenge.feature_marvel.data.model.events.MarvelEvent

data class CharacterState(
    val characterList: List<MarvelCharacter> = emptyList(),
    val eventList: List<MarvelEvent> = emptyList(),
    val showLoading: Boolean = false,
    val selectedCharacter: MarvelCharacter? = null,
)
