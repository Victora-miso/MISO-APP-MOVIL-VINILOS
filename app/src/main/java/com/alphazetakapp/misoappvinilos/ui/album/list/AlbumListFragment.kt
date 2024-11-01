package com.alphazetakapp.misoappvinilos.ui.album.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.*
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphazetakapp.misoappvinilos.R
import com.alphazetakapp.misoappvinilos.databinding.FragmentAlbumListBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.MenuProvider
import android.widget.Toast
import androidx.lifecycle.Lifecycle

@AndroidEntryPoint
class AlbumListFragment : Fragment() {

    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumListViewModel by viewModels()
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.loadAlbum()
    }

    private fun setupRecyclerView() {
        albumAdapter = AlbumAdapter { album ->
            // Por ahora solo mostraremos un log
            println("Album clicked: ${album.name}")
        }

        binding.albumsRecyclerView.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albumAdapter.submitList(albums)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.errorTextView.apply {
                visibility = if (error != null) View.VISIBLE else View.GONE
                text = error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}