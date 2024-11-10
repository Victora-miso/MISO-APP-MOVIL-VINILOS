package com.alphazetakapp.misoappvinilos.ui.collector.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alphazetakapp.misoappvinilos.databinding.FragmentCollectorDetailBinding
import com.alphazetakapp.misoappvinilos.ui.collector.detail.CollectorDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectorDetailFragment : Fragment() {
    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectorDetailViewModel by viewModels()
    private val args: CollectorDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadCollectors(args.collectorId)

    }

    private fun setupObservers() {
        viewModel.collectors.observe(viewLifecycleOwner) { collector ->
            binding.apply {
                collectorNameTextView.text = collector.name
                collectorEmailTextView.text = collector.email
                collectorTelephoneTextView.text = collector.telephone
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.errorTextView.apply {
                visibility = if (errorMessage != null) View.VISIBLE else View.GONE
                text = errorMessage
            }
        }
    }

}
