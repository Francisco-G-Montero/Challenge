package com.frommetoyou.interchallenge.feature_marvel.domain.model.characters

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)