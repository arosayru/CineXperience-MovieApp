package com.adithya.cinexperiencemovieapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adithya.cinexperiencemovieapp.R
import com.adithya.cinexperiencemovieapp.adapter.MovieListAdapter
import com.adithya.cinexperiencemovieapp.adapter.MovieAdapter
import com.adithya.cinexperiencemovieapp.databinding.FragmentHomeBinding
import com.adithya.cinexperiencemovieapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var searchAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerViews()
        observeViewModel()
        setupSearchBar()
        return binding.root
    }

    private fun setupRecyclerViews() {
        binding.popularRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        searchAdapter = MovieListAdapter(emptyList()) { selectedMovie ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToMovieDetailsFragment(selectedMovie)

            val options = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false)
                .build()

            findNavController().navigate(action, options)
        }
        binding.searchResultsRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.searchResultsRecycler.adapter = searchAdapter
    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            if (binding.searchBar.text.isNullOrEmpty()) {
                binding.popularRecycler.adapter = MovieAdapter(movies) { selectedMovie ->
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToMovieDetailsFragment(selectedMovie)

                    val options = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, false)
                        .build()

                    findNavController().navigate(action, options)
                }

                binding.popularRecycler.visibility = View.VISIBLE
                binding.popularTitle.visibility = View.VISIBLE
                binding.upcomingRecycler.visibility = View.VISIBLE
                binding.upcomingTitle.visibility = View.VISIBLE
                binding.searchResultsRecycler.visibility = View.GONE
                binding.emptyStateView.visibility = View.GONE
            }
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner) { movies ->
            if (binding.searchBar.text.isNullOrEmpty()) {
                binding.upcomingRecycler.adapter = MovieAdapter(movies) { selectedMovie ->
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToMovieDetailsFragment(selectedMovie)

                    val options = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, false)
                        .build()

                    findNavController().navigate(action, options)
                }
            }
        }

        viewModel.filteredMovies.observe(viewLifecycleOwner) { movies ->
            val isSearching = !binding.searchBar.text.isNullOrEmpty()

            if (isSearching) {
                searchAdapter.updateData(movies)
                binding.searchResultsRecycler.visibility = View.VISIBLE

                binding.popularRecycler.visibility = View.GONE
                binding.popularTitle.visibility = View.GONE
                binding.upcomingRecycler.visibility = View.GONE
                binding.upcomingTitle.visibility = View.GONE

                binding.emptyStateView.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
            } else {
                binding.searchResultsRecycler.visibility = View.GONE
                binding.emptyStateView.visibility = View.GONE
            }
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchMovies(query.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
