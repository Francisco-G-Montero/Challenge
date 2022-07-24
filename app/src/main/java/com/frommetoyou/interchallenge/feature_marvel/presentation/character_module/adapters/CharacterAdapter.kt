package com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.Result
import com.frommetoyou.interchallenge.databinding.ItemCharacterBinding

class CharacterAdapter(private var listener: OnClickListener) :
    ListAdapter<Result, RecyclerView.ViewHolder>(SnapshotDiffCallback()) {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = getItem(position)
        with(holder as ViewHolder) {
            binding.tvName.text = character.name
            binding.tvDescription.text = character.description
            Glide.with(mContext)
                .load("${character.thumbnail.path.replace("http","https")}.${character.thumbnail.extension}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivPhoto)
            setListener(character)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCharacterBinding.bind(view)
        fun setListener(character: Result) {
            binding.clCollapsed.setOnClickListener { listener.onClick(character) }
        }
    }

    class SnapshotDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}