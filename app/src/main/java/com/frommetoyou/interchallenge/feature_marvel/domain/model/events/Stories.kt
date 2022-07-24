package com.frommetoyou.interchallenge.feature_marvel.domain.model.events

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXXX>,
    val returned: Int
)