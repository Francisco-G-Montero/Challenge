package com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.adapters

import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.Result


interface OnClickListener {
    fun onClick(character: Result)
}