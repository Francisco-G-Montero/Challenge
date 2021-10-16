package com.frommetoyou.interchallenge.core.entities.events

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)