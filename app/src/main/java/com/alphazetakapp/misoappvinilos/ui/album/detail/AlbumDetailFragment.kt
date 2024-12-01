package com.alphazetakapp.misoappvinilos.ui.album.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.alphazetakapp.misoappvinilos.databinding.FragmentAlbumDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumDetailViewModel by viewModels()
    private lateinit var albumDetailAdapter: AlbumDetailAdapter
    private val args: AlbumDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumDetailAdapter = AlbumDetailAdapter()
        setupRecyclerView()
        setupObservers()
        viewModel.loadAlbum(args.albumId)
    }

    private fun setupObservers() {
        viewModel.album.observe(viewLifecycleOwner) { album ->
            binding.apply {
                albumNameTextView.text = album.name
                genreTextView.text = "Genre: ${album.genre}"
                releaseDateTextView.text = "Release Date: ${album.releaseDate}"
                recordLabelTextView.text = "Record Label: ${album.recordLabel}"
                descriptionTextView.text = album.description

                Glide.with(requireContext())
                    .load(album.cover)
                    .centerCrop()
                    .into(albumCoverImageView)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.errorTextView.apply {
                visibility = if (errorMessage != null) View.VISIBLE else View.GONE
                text = errorMessage
            }
        }
        viewModel.track.observe(viewLifecycleOwner) { tracks ->
            albumDetailAdapter.submitList(tracks)
        }
    }

    private fun setupRecyclerView() {
        binding.tracksRecyclerView.apply {
            adapter = albumDetailAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}