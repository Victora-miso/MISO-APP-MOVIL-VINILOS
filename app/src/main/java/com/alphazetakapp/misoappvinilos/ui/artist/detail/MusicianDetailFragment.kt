package com.alphazetakapp.misoappvinilos.ui.artist.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alphazetakapp.misoappvinilos.R
import com.alphazetakapp.misoappvinilos.databinding.FragmentMusicianDetailBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alphazetakapp.misoappvinilos.ui.artist.detail.MusicianDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicianDetailFragment: Fragment()  {

    private var _binding: FragmentMusicianDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicianDetailViewModel by viewModels()
    private val args: MusicianDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicianDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadMusician(args.musicianId)
    }

    private fun setupObservers() {
        viewModel.musician.observe(viewLifecycleOwner) { musician ->
            binding.apply {
                musicianNameTextView.text = musician.name
                //genreTextView.text = "Genre: ${musician.bi}"
                //releaseDateTextView.text = "Release Date: ${musician.releaseDate}"
                //recordLabelTextView.text = "Record Label: ${musician.recordLabel}"
                descriptionTextView.text = musician.description

                Glide.with(requireContext())
                    .load(musician.image)
                    .centerCrop()
                    .into(musicianImageView)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}