package com.alphazetakapp.misoappvinilos.ui.artist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alphazetakapp.misoappvinilos.data.model.Musician
import com.alphazetakapp.misoappvinilos.databinding.ItemMusicianBinding
import com.alphazetakapp.misoappvinilos.ui.artist.list.MusicianAdapter.MusicianDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MusicianAdapter (
    private val onMusicianClick: (Musician) -> Unit
) : ListAdapter<Musician, MusicianAdapter.MusicianViewHolder>(MusicianDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        return MusicianViewHolder(
            ItemMusicianBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        val musician = getItem(position)
        holder.bind(musician)

        holder.itemView.setOnClickListener {
            onMusicianClick(musician)
        }
    }

    inner class MusicianViewHolder(
        private val binding: ItemMusicianBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMusicianClick(getItem(position))
                }
            }
        }

        fun bind(musician: Musician) {
            binding.apply {
                musicianNameTextView.text = musician.name
                birthDateTextView.text = musician.birthDate

                Glide.with(musicianImageView)
                    .load(musician.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(musicianImageView)
            }
        }
    }

    class MusicianDiffCallback : DiffUtil.ItemCallback<Musician>() { //This Class helps to performance
        override fun areItemsTheSame(oldItem: Musician, newItem: Musician): Boolean {
            // Comprueba si dos items son el mismo elemento
            // Por ejemplo: mismo ID pero tal vez contenido diferente
            // Aquí tengo la duda, porque el modelo no muesstra un ID para los álbumes
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Musician, newItem: Musician): Boolean {
            return oldItem == newItem
        }
    }
}