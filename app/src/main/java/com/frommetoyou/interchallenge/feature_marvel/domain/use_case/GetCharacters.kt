package com.frommetoyou.interchallenge.feature_marvel.domain.use_case

import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.CharacterResponse
import com.frommetoyou.interchallenge.feature_marvel.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetCharacters(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(offset: Int) : Response<CharacterResponse> {
        return repository.getCharactes(offset)
    }
}