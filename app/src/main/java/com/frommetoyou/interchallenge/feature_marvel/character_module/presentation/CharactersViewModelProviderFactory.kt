package com.frommetoyou.interchallenge.feature_marvel.character_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.feature_marvel.CharactersViewModel

class CharactersViewModelProviderFactory(
    val charactersRepository: CharactersRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(charactersRepository) as T
    }
}