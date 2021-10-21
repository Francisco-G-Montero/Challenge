package com.frommetoyou.interchallenge.feature_marvel.data.repository

import com.frommetoyou.interchallenge.feature_marvel.data.data_source.CharactersAPI
import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.repository.CharacterRepository
import retrofit2.Response

class CharactersRepositoryImpl(private val charactersAPI: CharactersAPI): CharacterRepository{
    override suspend fun getCharactes(offset: Int): Response<CharacterResponse> {
        return charactersAPI.getCharacters(offset = offset)
    }
    override suspend fun getEvents() = charactersAPI.getEvents()
}