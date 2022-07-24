package com.frommetoyou.interchallenge.feature_marvel.domain.use_case

import com.frommetoyou.interchallenge.feature_marvel.domain.model.events.Events
import com.frommetoyou.interchallenge.feature_marvel.domain.repository.CharacterRepository
import retrofit2.Response

class GetEvents(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): Response<Events> {
        return repository.getEvents()
    }
}