package com.alphazetakapp.misoappvinilos.ui.collector.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.alphazetakapp.misoappvinilos.databinding.FragmentCollectorListBinding

@AndroidEntryPoint
class CollectorListFragment : Fragment() {
    private var _binding: FragmentCollectorListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectorListViewModel by viewModels()
    private lateinit var collectorAdapter: CollectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.loadCollectors()

    }

    private fun setupRecyclerView() {
        collectorAdapter = CollectorAdapter { collector ->
            val action = CollectorListFragmentDirections.actionCollectorListToDetail(collector.id)
            findNavController().navigate(action)
        }

        binding.CollectorRecyclerView.apply {
            adapter = collectorAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            collectorAdapter.submitList(collectors)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.LoadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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