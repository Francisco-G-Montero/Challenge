package com.frommetoyou.interchallenge.feature_marvel.data.data_source

import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.model.events.Events
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.API_KEY
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.HASH
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.ORDER_BY_START_DATE
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.QUERY_CHARACTERS_PAGE_SIZE
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.QUERY_EVENTS_PAGE_SIZE
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.TIMESTAMP
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPI {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit")
        limit: Int = QUERY_CHARACTERS_PAGE_SIZE,
        @Query("offset")
        offset: Int = 0,
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("hash")
        hash: String = HASH,
        @Query("ts")
        ts: Int = TIMESTAMP,
    ): Response<CharacterResponse>
    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("limit")
        limit: Int = QUERY_EVENTS_PAGE_SIZE,
        @Query("orderBy")
        orderBy: String = ORDER_BY_START_DATE,
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("hash")
        hash: String = HASH,
        @Query("ts")
        ts: Int = TIMESTAMP,
    ): Response<Events>
}