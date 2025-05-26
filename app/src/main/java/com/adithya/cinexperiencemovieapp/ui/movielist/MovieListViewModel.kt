package com.adithya.cinexperiencemovieapp.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.repository.MovieRepository
import com.adithya.cinexperiencemovieapp.utils.GenreUtils
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> = _genres

    private val allMovies = mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            val fetched = repository.getPopularMovies()
            allMovies.clear()
            allMovies.addAll(fetched)
            _movies.value = fetched
            updateGenreFilters(fetched)
        }
    }

    private fun updateGenreFilters(movies: List<Movie>) {
        val allGenreIds = movies.flatMap { it.genre_ids }.distinct()
        val genreNames = allGenreIds.mapNotNull { GenreUtils.genreMap[it] }
            .distinct()
            .sorted()
            .toMutableList()
        genreNames.add(0, "All") // Add "All" to the beginning
        _genres.value = genreNames
    }

    fun filterByQuery(query: String) {
        val result = if (query.isEmpty()) {
            allMovies
        } else {
            allMovies.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        _movies.value = result
    }

    fun filterByGenre(genreName: String) {
        if (genreName.equals("All", ignoreCase = true)) {
            _movies.value = allMovies
            return
        }

        val genreId = GenreUtils.genreMap.entries.find { it.value.equals(genreName, ignoreCase = true) }?.key
        if (genreId != null) {
            _movies.value = allMovies.filter { it.genre_ids.contains(genreId) }
        }
    }
}
