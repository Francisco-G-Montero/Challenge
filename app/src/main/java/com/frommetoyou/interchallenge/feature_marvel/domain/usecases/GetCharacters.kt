package com.frommetoyou.interchallenge.feature_marvel.domain.usecases

import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.repository.CharacterRepository
import retrofit2.Response

class GetCharacters(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(offset: Int) : Response<CharacterResponse> {
        return repository.getCharactes(offset)
    }
}