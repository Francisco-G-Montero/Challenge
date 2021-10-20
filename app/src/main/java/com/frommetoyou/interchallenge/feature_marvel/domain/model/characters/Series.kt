package com.frommetoyou.interchallenge.feature_marvel.domain.model.characters

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)