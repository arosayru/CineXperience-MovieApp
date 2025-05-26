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

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val allMovies = mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            val fetched = repository.getPopularMovies()
            allMovies.clear()
            allMovies.addAll(fetched)
            _movies.value = fetched
            _isEmpty.value = fetched.isEmpty()
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
        _isEmpty.value = result.isEmpty()
    }

    fun filterByGenre(genreName: String) {
        val result = if (genreName.equals("All", ignoreCase = true)) {
            allMovies
        } else {
            val genreId = GenreUtils.genreMap.entries.find { it.value.equals(genreName, ignoreCase = true) }?.key
            if (genreId != null) {
                allMovies.filter { it.genre_ids.contains(genreId) }
            } else {
                emptyList()
            }
        }

        _movies.value = result
        _isEmpty.value = result.isEmpty()
    }
}
