package com.frommetoyou.interchallenge.core.entities.events

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)