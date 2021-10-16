package com.frommetoyou.interchallenge.core.repository

import com.frommetoyou.interchallenge.core.api.RetrofitInstance

class CharactersRepository{
    suspend fun getCharacters() = RetrofitInstance.api.getCharacters()
    suspend fun getEvents() = RetrofitInstance.api.getEvents()
}