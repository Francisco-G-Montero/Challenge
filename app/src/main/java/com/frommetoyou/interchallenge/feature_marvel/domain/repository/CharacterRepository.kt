package com.frommetoyou.interchallenge.feature_marvel.domain.repository

import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.data.model.events.Events
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharactes(offset: Int): Response<CharacterResponse>

    suspend fun getEvents(): Response<Events>
}