package com.frommetoyou.interchallenge.feature_marvel.domain.model.characters

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)