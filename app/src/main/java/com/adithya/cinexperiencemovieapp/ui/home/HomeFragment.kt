package com.adithya.cinexperiencemovieapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adithya.cinexperiencemovieapp.adapter.MovieAdapter
import com.adithya.cinexperiencemovieapp.databinding.FragmentHomeBinding
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.ui.home.HomeFragmentDirections
import com.adithya.cinexperiencemovieapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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
    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            binding.popularRecycler.adapter = MovieAdapter(movies) { selectedMovie ->
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieDetailsFragment(selectedMovie)
                findNavController().navigate(action)
            }
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner) { movies ->
            binding.upcomingRecycler.adapter = MovieAdapter(movies) { selectedMovie ->
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieDetailsFragment(selectedMovie)
                findNavController().navigate(action)
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
