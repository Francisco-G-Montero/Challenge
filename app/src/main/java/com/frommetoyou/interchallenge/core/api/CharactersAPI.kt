package com.frommetoyou.interchallenge.core.api

import com.frommetoyou.interchallenge.core.entities.characters.CharacterResponse
import com.frommetoyou.interchallenge.core.entities.events.Events
import com.frommetoyou.interchallenge.core.util.Constants.Companion.API_KEY
import com.frommetoyou.interchallenge.core.util.Constants.Companion.HASH
import com.frommetoyou.interchallenge.core.util.Constants.Companion.TIMESTAMP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPI {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("hash")
        hash: String = HASH,
        @Query("ts")
        ts: Int = TIMESTAMP,
    ): Response<CharacterResponse>
    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("hash")
        hash: String = HASH,
        @Query("ts")
        ts: Int = TIMESTAMP,
    ): Response<Events>
}