package com.frommetoyou.interchallenge.feature_marvel.data.model.events

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    var results: List<MarvelEvent>,
    val total: Int
)