package com.frommetoyou.interchallenge.core.entities.characters

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)