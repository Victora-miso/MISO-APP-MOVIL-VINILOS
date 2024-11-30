package com.alphazetakapp.misoappvinilos.ui.album.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.compose.ui.text.intl.Locale
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alphazetakapp.misoappvinilos.databinding.FragmentAlbumCreateBinding
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@AndroidEntryPoint
class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentAlbumCreateBinding? = null
    private val binding get() = _binding!!

    private val createAlbumViewModel: CreateAlbumViewModel by viewModels()
    var selectedGenre:String = "None"
    var selectedLabel:String = "None"
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
        val spinner: Spinner = binding.inputGenreSpinner
        val spinner2: Spinner = binding.inputLabelSpinner

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGenre = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Manejar si no se selecciona nada (opcional)
            }
        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedLabel = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Manejar si no se selecciona nada (opcional)
            }
        }
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")
            binding.createAlbumButton.setOnClickListener {
                var dateraw = binding.inputDateText.text.toString()
                var finalDate: Date?
                try {
                    val parsedDate = inputFormat.parse(dateraw)
                    dateraw = outputFormat.format(parsedDate!!)
                    finalDate = outputFormat.parse(dateraw)
                } catch (e: ParseException){
                    println("$e")
                    finalDate = outputFormat.parse(dateraw)
                }
                val name = binding.inputTitleText.text.toString()
                val cover = binding.inputCoverText.text.toString()
                val releaseDate = finalDate
                    ?: throw IllegalArgumentException("Invalid date format or null date")
                val description = binding.inputDescriptionText.text.toString()
                val genre = selectedGenre
                val recordLabel = selectedLabel
                val album = CreateAlbum(
                    name = name,
                    cover = cover,
                    description = description,
                    genre = genre,
                    releaseDate = releaseDate,
                    recordLabel = recordLabel
                )

                createAlbumViewModel.createAlbum(album)
            }
        } catch (e: ParseException) {
            Toast.makeText(requireContext(), "Error de formato de fecha: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
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