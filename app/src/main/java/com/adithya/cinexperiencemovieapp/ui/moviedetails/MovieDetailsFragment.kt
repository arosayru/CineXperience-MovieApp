package com.adithya.cinexperiencemovieapp.ui.moviedetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adithya.cinexperiencemovieapp.R
import com.adithya.cinexperiencemovieapp.adapter.CategoryAdapter
import com.adithya.cinexperiencemovieapp.databinding.FragmentMovieDetailsBinding
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.utils.GenreUtils
import com.bumptech.glide.Glide
import androidx.navigation.fragment.navArgs

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter

    // Use Safe Args to get the passed movie object
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupGenresRecycler()

        bindMovieDetails(args.movie)
    }

    private fun setupSearchBar() {
        binding.searchBar.apply {
            // Disabled for now or use if you want search here
            isEnabled = false

            // Optional: Add TextWatcher if you want to implement search here
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setupGenresRecycler() {
        categoryAdapter = CategoryAdapter(emptyList()) {}
        binding.genresRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecycler.adapter = categoryAdapter
    }

    private fun bindMovieDetails(movie: Movie) {
        binding.titleText.text = movie.title
        binding.ratingText.text = String.format("%.1f", movie.vote_average)
        binding.overviewText.text = movie.overview

        // Load poster image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .placeholder(R.drawable.ic_movies)
            .into(binding.posterImage)

        // Map genre ids to genre names using GenreUtils
        val genreNames = movie.genre_ids.mapNotNull { GenreUtils.genreMap[it] }
        categoryAdapter.updateData(genreNames)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
