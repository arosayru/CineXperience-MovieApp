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

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _filteredMovies = MutableLiveData<List<Movie>>()
    val filteredMovies: LiveData<List<Movie>> = _filteredMovies

    private val fullMovieList = mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            val popular = repository.getPopularMovies()
            val upcoming = repository.getUpcomingMovies()

            fullMovieList.clear()
            fullMovieList.addAll(popular)
            fullMovieList.addAll(upcoming)

            _popularMovies.value = popular
            _upcomingMovies.value = upcoming
            _filteredMovies.value = fullMovieList
        }
    }

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
