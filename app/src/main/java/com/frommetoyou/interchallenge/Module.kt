package com.frommetoyou.interchallenge

import com.frommetoyou.interchallenge.core.repository.CharactersRepository
import com.frommetoyou.interchallenge.feature_marvel.CharactersViewModel
import org.koin.dsl.module

val appModule = module {

    single { CharactersRepository() }

    single { CharactersViewModel(get())}

}