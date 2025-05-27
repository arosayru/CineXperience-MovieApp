package com.adithya.cinexperiencemovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    // LiveData exposed to UI
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _filteredMovies = MutableLiveData<List<Movie>>()
    val filteredMovies: LiveData<List<Movie>> = _filteredMovies

    // Holds full combined list for search filtering
    private val fullMovieList = mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            val popular = repository.getPopularMovies()
            val upcoming = repository.getUpcomingMovies()

            // Combine both lists for search filtering
            fullMovieList.clear()
            fullMovieList.addAll(popular)
            fullMovieList.addAll(upcoming)

            // Initialize live data
            _popularMovies.value = popular
            _upcomingMovies.value = upcoming
            _filteredMovies.value = fullMovieList // Initially show all combined movies
        }
    }

    // Search/filter function across all movies
    fun searchMovies(query: String) {
        if (query.isEmpty()) {
            _filteredMovies.value = fullMovieList
        } else {
            _filteredMovies.value = fullMovieList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}
