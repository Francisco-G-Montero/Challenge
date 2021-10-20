package com.frommetoyou.interchallenge.feature_marvel.domain.repository

import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.model.events.Events
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharactes(offset: Int): Response<CharacterResponse>

    suspend fun getEvents(): Response<Events>
}