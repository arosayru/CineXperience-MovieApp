package com.adithya.cinexperiencemovieapp.ui.movielist

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
import com.adithya.cinexperiencemovieapp.adapter.CategoryAdapter
import com.adithya.cinexperiencemovieapp.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var adapter: MovieListAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MovieListAdapter(emptyList()) { movie ->
            val action = MovieListFragmentDirections
                .actionMovieListFragmentToMovieDetailsFragment(movie)

            val options = NavOptions.Builder()
                .setPopUpTo(R.id.movieListFragment, false)
                .build()

            findNavController().navigate(action, options)
        }

        binding.movieListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.movieListRecycler.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.updateData(movies)

            // Fix visibility toggle using container ID
            binding.movieListRecycler.visibility =
                if (movies.isEmpty()) View.GONE else View.VISIBLE
            binding.emptyStateContainer.visibility =
                if (movies.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            setupCategoryRecyclerView(genres)
        }

        setupSearch()
    }

    private fun setupSearch() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterByQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupCategoryRecyclerView(categories: List<String>) {
        categoryAdapter = CategoryAdapter(categories) { selectedGenre ->
            viewModel.filterByGenre(selectedGenre)
        }
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.adapter = categoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
