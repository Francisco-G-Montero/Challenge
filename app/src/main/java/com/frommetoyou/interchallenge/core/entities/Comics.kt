package com.frommetoyou.interchallenge.core.entities

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)