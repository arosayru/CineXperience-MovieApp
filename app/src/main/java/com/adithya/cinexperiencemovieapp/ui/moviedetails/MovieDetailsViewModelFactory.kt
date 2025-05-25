package com.adithya.cinexperiencemovieapp.ui.moviedetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adithya.cinexperiencemovieapp.repository.MovieRepository

class MovieDetailsViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(MovieRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
