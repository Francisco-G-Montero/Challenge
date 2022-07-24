package com.frommetoyou.interchallenge.feature_marvel.domain.model.events

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)