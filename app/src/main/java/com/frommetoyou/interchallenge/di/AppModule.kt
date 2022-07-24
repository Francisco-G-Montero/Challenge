package com.frommetoyou.interchallenge.di

import com.frommetoyou.interchallenge.feature_marvel.data.data_source.CharactersAPI
import com.frommetoyou.interchallenge.feature_marvel.data.repository.CharactersRepositoryImpl
import com.frommetoyou.interchallenge.feature_marvel.domain.repository.CharacterRepository
import com.frommetoyou.interchallenge.feature_marvel.domain.use_case.GetCharacters
import com.frommetoyou.interchallenge.feature_marvel.domain.use_case.GetEvents
import com.frommetoyou.interchallenge.feature_marvel.domain.use_case.MarvelUseCases
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCharacterAPI(): CharactersAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterRepository(charactersAPI: CharactersAPI): CharacterRepository {
        return CharactersRepositoryImpl(charactersAPI)
    }

    @Singleton
    @Provides
    fun provideMarvelUseCases(characterRepository: CharacterRepository): MarvelUseCases{
        return MarvelUseCases(
            getCharacters = GetCharacters(characterRepository),
            getEvents = GetEvents(characterRepository)
        )
    }
}