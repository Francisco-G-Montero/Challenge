package com.frommetoyou.interchallenge.core.repository

import com.frommetoyou.interchallenge.core.api.RetrofitInstance

class CharactersRepository{
    suspend fun getCharacters(offset: Int = 0) = RetrofitInstance.api.getCharacters(offset = offset)
    suspend fun getEvents() = RetrofitInstance.api.getEvents()
}