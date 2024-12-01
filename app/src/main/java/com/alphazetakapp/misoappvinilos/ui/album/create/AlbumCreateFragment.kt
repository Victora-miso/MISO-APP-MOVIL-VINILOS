package com.alphazetakapp.misoappvinilos.ui.album.create

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alphazetakapp.misoappvinilos.R
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import com.alphazetakapp.misoappvinilos.databinding.FragmentAlbumCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentAlbumCreateBinding? = null
    private val binding get() = _binding!!

    private val createAlbumViewModel: CreateAlbumViewModel by viewModels()
    private var selectedGenre: String = "None"
    private var selectedLabel: String = "None"

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
        setupSpinners()

        binding.inputDateText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Formato de fecha deseado
                    val formattedDate = String.format(
                        Locale.getDefault(),
                        "%04d-%02d-%02dT00:00:00.000Z",
                        selectedYear,
                        selectedMonth + 1, // Los meses empiezan en 0
                        selectedDay
                    )
                    binding.inputDateText.setText(formattedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.createAlbumButton.setOnClickListener {
            if (validateInputs()) {
                val album = createAlbum()
                createAlbumViewModel.createAlbum(album)
            }
        }
    }

    private fun setupSpinners() {
        binding.inputGenreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedGenre = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedGenre = "None"
            }
        }

        binding.inputLabelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedLabel = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedLabel = "None"
            }
        }
    }

    private fun validateInputs(): Boolean {
        val name = binding.inputTitleText.text.toString()
        val cover = binding.inputCoverText.text.toString()
        val date = binding.inputDateText.text.toString()

        return when {
            name.isBlank() -> {
                showToast(getString(R.string.error_name_required))
                false
            }
            cover.isBlank() -> {
                showToast(getString(R.string.error_cover_required))
                false
            }
            date.isBlank() -> {
                showToast(getString(R.string.error_date_required))
                false
            }
            else -> true
        }
    }

    private fun createAlbum(): CreateAlbum {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val releaseDate = dateFormat.parse(binding.inputDateText.text.toString())

        return CreateAlbum(
            name = binding.inputTitleText.text.toString(),
            cover = binding.inputCoverText.text.toString(),
            description = binding.inputDescriptionText.text.toString(),
            genre = selectedGenre,
            releaseDate = releaseDate!!,
            recordLabel = selectedLabel
        )
    }

    private fun setupObservers() {
        createAlbumViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        createAlbumViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                binding.errorTextView.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            } ?: run { binding.errorTextView.visibility = View.GONE }
        }

        createAlbumViewModel.albumCreated.observe(viewLifecycleOwner) { newAlbum ->
            newAlbum?.let {
                showToast(getString(R.string.album_created_success))
                val action = CreateAlbumFragmentDirections.actionAlbumCreateToDetail(it.id)
                findNavController().navigate(action)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}