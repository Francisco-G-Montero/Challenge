package com.frommetoyou.interchallenge.feature_marvel.presentation.events_module.adapters

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.domain.model.events.Result
import com.frommetoyou.interchallenge.databinding.ItemEventBinding
import com.frommetoyou.interchallenge.feature_marvel.presentation.character_module.adapters.ComicAdapter
import java.text.SimpleDateFormat

class EventsAdapter() :
    ListAdapter<Result, RecyclerView.ViewHolder>(SnapshotDiffCallback()) {
    private lateinit var mContext: Context
    private lateinit var mComicAdapter: ComicAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = getItem(position)
        with(holder as ViewHolder) {
            binding.tvTitle.text = event.title

            event.start?.let {
                val date = SimpleDateFormat("yyyy-MM-dd").parse(event.start)
                val finalDate = SimpleDateFormat("dd 'de' MMMM, yyyy").format(date!!)
                binding.tvDate.text = finalDate
            }

            //binding.tvDate.text = date.toString()
            Glide.with(mContext).load("${event.thumbnail.path.replace("http", "https")}.${event.thumbnail.extension}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivPhoto)
            mComicAdapter = ComicAdapter()
            binding.recyclerViewComics.apply {
                adapter = mComicAdapter
                layoutManager = LinearLayoutManager(mContext)
            }
            event.comics.items?.let { mComicAdapter.submitList(it) }
            binding.btnExpand.setOnClickListener { expandComicList() }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemEventBinding.bind(view)
        fun expandComicList() {
            TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
            binding.btnExpand.isActivated = !binding.btnExpand.isActivated
            binding.clExpanded.visibility =
                if (binding.btnExpand.isActivated) View.VISIBLE else View.GONE
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