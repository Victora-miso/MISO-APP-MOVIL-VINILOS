package com.alphazetakapp.misoappvinilos.ui.album.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alphazetakapp.misoappvinilos.data.model.Track
import com.alphazetakapp.misoappvinilos.databinding.ItemTrackBinding

class AlbumDetailAdapter() :
ListAdapter<Track, AlbumDetailAdapter.ViewHolder>(AlbumDetailAdapterDiffCallback()) {
    inner class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
            }
        }
        fun bind(track: Track) {
            binding.apply {
                trackNameTextView.text = track.name
                trackDurationTextView.text = track.duration
            }
        }
    }

    class AlbumDetailAdapterDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}