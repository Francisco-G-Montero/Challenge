package com.frommetoyou.interchallenge.feature_marvel.data.model.characters

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<MarvelCharacter>,
    val total: Int
)