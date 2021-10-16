package com.frommetoyou.interchallenge.events_module.adapters

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.character_module.adapters.ComicAdapter
import com.frommetoyou.interchallenge.core.entities.characters.Item
import com.frommetoyou.interchallenge.databinding.ItemCharacterBinding
import com.frommetoyou.interchallenge.core.entities.events.Result
import com.frommetoyou.interchallenge.core.entities.events.Events
import com.frommetoyou.interchallenge.databinding.ItemEventBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EventsAdapter() :
    ListAdapter<Result, RecyclerView.ViewHolder>(SnapshotDiffCallback()) {
    private lateinit var mContext: Context
    private lateinit var mComicAdapter: ComicAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = getItem(position)
        with(holder as ViewHolder) {
            binding.tvTitle.text = event.title
            //binding.tvDate.text = SimpleDateFormat("EEEE, dd MMMM yyyy").format(event.end.)
            // binding.tvDate.text = SimpleDateFormat("EEEE, dd MMMM yyyy").parse(event.end).toString()

            event.end?.let {
                val date = SimpleDateFormat("yyyy-MM-dd").parse(event.end)
                binding.tvDate.text = date.toString()
            }

            //binding.tvDate.text = date.toString()
            Glide.with(mContext)
                .load(
                    "${
                        event.thumbnail.path.replace(
                            "http",
                            "https"
                        )
                    }.${event.thumbnail.extension}"
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivPhoto)
            mComicAdapter = ComicAdapter()
            binding.recyclerViewComics.apply {
                adapter = mComicAdapter
                layoutManager = LinearLayoutManager(mContext)
            }
            event.comics.items?.let {
                mComicAdapter.submitList(it)
            }
            binding.btnExpand.setOnClickListener { expandComicList() }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemEventBinding.bind(view)
        fun expandComicList() {
            TransitionManager. beginDelayedTransition(binding.root, AutoTransition())
            binding.btnExpand.isActivated = !binding.btnExpand.isActivated
            binding.clExpanded.visibility = if ( binding.btnExpand.isActivated ) View.VISIBLE else View.GONE
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