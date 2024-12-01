package com.alphazetakapp.misoappvinilos.ui.album.associate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import com.alphazetakapp.misoappvinilos.databinding.FragmentAssociateTrackBinding
import com.alphazetakapp.misoappvinilos.data.model.Track
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssociateTrackFragment : Fragment() {

    private var _binding: FragmentAssociateTrackBinding? = null
    private val binding get() = _binding!!

    private val associateTrackViewModel: AssociateTrackViewModel by viewModels()

    private var selectedAlbumId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssociateTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupAlbumDropdown()

        binding.createTrackButton.setOnClickListener {
            val trackName = binding.inputTrackNameText.text.toString()
            val trackDuration = binding.inputDurationText.text.toString()

            if (trackName.isBlank() || trackDuration.isBlank() || selectedAlbumId == null) {
                binding.errorTextView.apply {
                    text = "Please fill in all fields and select an album."
                    visibility = View.VISIBLE
                }
                return@setOnClickListener
            }
            val track = Track(name = trackName, duration = trackDuration)
            println("Album ID: ${selectedAlbumId!!}")
            println("Track Name: $trackName")
            println("Track Duration: $trackDuration")
            associateTrackViewModel.associateTrackToAlbum(selectedAlbumId!!, track)
        }
    }

    private fun setupAlbumDropdown() {
        associateTrackViewModel.albums.observe(viewLifecycleOwner) { albums ->
            if (albums != null) {
                if (albums.isNotEmpty()) {
                    val albumNames = albums.map { it.name }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, albumNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = adapter

                    binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            selectedAlbumId = albums[position].id
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            selectedAlbumId = null
                        }
                    }
                } else {
                    binding.errorTextView.apply {
                        visibility = View.VISIBLE
                        text = "No albums available."
                    }
                }
            }
        }
    }

    private fun setupObservers() {
        associateTrackViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        associateTrackViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.errorTextView.apply {
                visibility = if (errorMessage != null) View.VISIBLE else View.GONE
                text = errorMessage
            }
        }
        associateTrackViewModel.trackAssociated.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                val success = result.getOrNull() ?: false

                if (success) {
                    Toast.makeText(requireContext(), "Track asociado con éxito", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    binding.errorTextView.apply {
                        visibility = View.VISIBLE
                        text = "Error al asociar el track con el álbum."
                    }
                }
            } else {
                // Si la operación falló, manejar el error aquí
                binding.errorTextView.apply {
                    visibility = View.VISIBLE
                    text = "Error al asociar el track con el álbum."
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}