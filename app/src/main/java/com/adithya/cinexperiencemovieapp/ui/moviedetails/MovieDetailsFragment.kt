package com.adithya.cinexperiencemovieapp.ui.moviedetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adithya.cinexperiencemovieapp.adapter.FeedbackAdapter
import com.adithya.cinexperiencemovieapp.adapter.GenreAdapter
import com.adithya.cinexperiencemovieapp.databinding.FragmentMovieDetailsBinding
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.utils.GenreUtils
import com.bumptech.glide.Glide

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var feedbackAdapter: FeedbackAdapter

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(requireContext())
    }

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
        setupFeedbacksRecycler()
        bindMovieDetails(args.movie)
        observeFeedbacks()
        viewModel.loadFeedbacks(args.movie.id)
    }

    private fun setupSearchBar() {
        binding.searchBar.apply {
            isEnabled = false
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setupGenresRecycler() {
        genreAdapter = GenreAdapter(emptyList())
        binding.genresRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecycler.adapter = genreAdapter
    }

    private fun setupFeedbacksRecycler() {
        feedbackAdapter = FeedbackAdapter(emptyList())
        binding.feedbackRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.feedbackRecycler.adapter = feedbackAdapter
    }

    private fun observeFeedbacks() {
        viewModel.feedbacks.observe(viewLifecycleOwner) { feedbackList ->
            feedbackAdapter.updateData(feedbackList)
        }
    }

    private fun bindMovieDetails(movie: Movie) {
        binding.titleText.text = movie.title
        binding.ratingText.text = String.format("%.1f", movie.vote_average)
        binding.overviewText.text = movie.overview

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .placeholder(com.adithya.cinexperiencemovieapp.R.drawable.ic_movies)
            .into(binding.posterImage)

        val genreNames = movie.genre_ids.mapNotNull { GenreUtils.genreMap[it] }
        genreAdapter.updateData(genreNames)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
