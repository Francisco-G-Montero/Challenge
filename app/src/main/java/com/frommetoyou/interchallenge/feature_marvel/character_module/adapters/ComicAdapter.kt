package com.frommetoyou.interchallenge.feature_marvel.character_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.core.entities.characters.Item
import com.frommetoyou.interchallenge.databinding.ItemComicBinding

class ComicAdapter() :
    ListAdapter<Item, RecyclerView.ViewHolder>(SnapshotDiffCallback()) {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_comic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comic = getItem(position)
        with(holder as ViewHolder) {
            binding.tvTitle.text = comic.name
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemComicBinding.bind(view)
    }

    class SnapshotDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}