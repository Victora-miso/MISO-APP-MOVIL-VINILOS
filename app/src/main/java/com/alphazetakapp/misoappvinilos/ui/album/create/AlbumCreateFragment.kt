package com.alphazetakapp.misoappvinilos.ui.album.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alphazetakapp.misoappvinilos.databinding.FragmentAlbumCreateBinding
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.TimeZone

@AndroidEntryPoint
class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentAlbumCreateBinding? = null
    private val binding get() = _binding!!

    private val createAlbumViewModel: CreateAlbumViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        dateFormat.timeZone = TimeZone.getTimeZone("America/Bogota")
        binding.createAlbumButton.setOnClickListener {
            val name = binding.inputTitleText.text.toString()
            val cover = binding.inputCoverText.text.toString()
            val releaseDate = dateFormat.parse(binding.inputDateText.text.toString())
                ?: throw IllegalArgumentException("Invalid date format or null date")
            val description = binding.inputDescriptionText.text.toString()
            val genre = binding.inputGenreText.text.toString()
            val recordLabel = binding.inputLabelText.text.toString()
            val album = CreateAlbum(name = name, cover = cover, description = description, genre = genre, releaseDate = releaseDate, recordLabel = recordLabel)

            createAlbumViewModel.createAlbum(album)
        }
    }

    private fun setupObservers() {
        createAlbumViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        createAlbumViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.errorTextView.apply {
                visibility = if (errorMessage != null) View.VISIBLE else View.GONE
                text = errorMessage
            }
        }

        createAlbumViewModel.albumCreated.observe(viewLifecycleOwner) { newAlbum ->
            newAlbum?.let {
                // Redirigir a la vista del detalle del álbum recién creado
                val action = CreateAlbumFragmentDirections.actionAlbumCreateToDetail(it.id)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}