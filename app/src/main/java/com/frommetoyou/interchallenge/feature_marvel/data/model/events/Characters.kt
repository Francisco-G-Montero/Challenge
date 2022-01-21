package com.frommetoyou.interchallenge.feature_marvel.data.model.events

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)