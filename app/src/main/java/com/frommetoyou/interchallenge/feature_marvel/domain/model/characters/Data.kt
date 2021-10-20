package com.frommetoyou.interchallenge.feature_marvel.domain.model.characters

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<Result>,
    val total: Int
)