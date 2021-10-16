package com.frommetoyou.interchallenge.core.entities

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)