package com.frommetoyou.interchallenge

import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.feature_marvel.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { CharactersRepository() }

    // MyViewModel ViewModel
    viewModel { CharactersViewModel(get()) }
}