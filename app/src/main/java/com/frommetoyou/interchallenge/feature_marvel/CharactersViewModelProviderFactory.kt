package com.frommetoyou.interchallenge.feature_marvel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frommetoyou.interchallenge.core.repository.CharactersRepository

class CharactersViewModelProviderFactory(
    val charactersRepository: CharactersRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(charactersRepository) as T
    }
}