package com.alphazetakapp.misoappvinilos.ui.artist.list

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
import com.alphazetakapp.misoappvinilos.databinding.FragmentMusicianListBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.MenuProvider
import android.widget.Toast
import androidx.lifecycle.Lifecycle



@AndroidEntryPoint
class MusicianListFragment : Fragment() {
    private var _binding: FragmentMusicianListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MusicianListViewModel by viewModels()
    private lateinit var musicianAdapter: MusicianAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicianListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.loadMusician()
    }

    private fun setupRecyclerView() {
        musicianAdapter = MusicianAdapter { musician ->
            // Por ahora solo mostraremos un log
            val action = MusicianListFragmentDirections.actionMusicianListToDetail(musician.id)
            findNavController().navigate(action)
        }

        binding.musiciansRecyclerView.apply {
            adapter = musicianAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        viewModel.musicians.observe(viewLifecycleOwner) { musicians ->
            musicianAdapter.submitList(musicians)
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