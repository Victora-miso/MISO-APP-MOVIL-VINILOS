package com.alphazetakapp.misoappvinilos.ui.album.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.databinding.ItemAlbumBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumAdapter(
    private val onAlbumClick: (Album) -> Unit
) : ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AlbumViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAlbumClick(getItem(position))
                }
            }
        }

        fun bind(album: Album) {
            binding.apply {
                albumNameTextView.text = album.name
                genreTextView.text = album.genre
                releaseDateTextView.text = album.releaseDate

                Glide.with(albumCoverImageView)
                    .load(album.cover)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(albumCoverImageView)
            }
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() { //This Class helps to performance
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            // Comprueba si dos items son el mismo elemento
            // Por ejemplo: mismo ID pero tal vez contenido diferente
            // Aquí tengo la duda, porque el modelo no muesstra un ID para los álbumes
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}