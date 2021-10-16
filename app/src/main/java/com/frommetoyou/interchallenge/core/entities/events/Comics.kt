package com.frommetoyou.interchallenge.core.entities.events

import com.frommetoyou.interchallenge.core.entities.characters.Item

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)