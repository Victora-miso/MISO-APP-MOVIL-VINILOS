package com.alphazetakapp.misoappvinilos.ui.collector.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alphazetakapp.misoappvinilos.data.model.Collector
import com.alphazetakapp.misoappvinilos.databinding.ItemCollectorBinding

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CollectorAdapter (
    private val onCollectorClick: (Collector) -> Unit
): ListAdapter<Collector, CollectorAdapter.CollectorViewHolder>(CollectorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        return CollectorViewHolder(
            ItemCollectorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CollectorViewHolder (
        private val binding: ItemCollectorBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCollectorClick(getItem(position))
                }
            }
        }

        fun bind(collector: Collector) {
            binding.apply {
                collectorNameTextView.text = collector.name
            }
        }
    }

    class CollectorDiffCallback : DiffUtil.ItemCallback<Collector>() {
        override fun areItemsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem == newItem
        }
    }
}