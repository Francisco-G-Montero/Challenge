package com.frommetoyou.interchallenge.core.entities.events

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    var results: List<Result>,
    val total: Int
)