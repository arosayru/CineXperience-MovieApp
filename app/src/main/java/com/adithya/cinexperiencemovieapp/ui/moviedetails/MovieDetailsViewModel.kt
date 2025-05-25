package com.adithya.cinexperiencemovieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adithya.cinexperiencemovieapp.model.Feedback
import com.adithya.cinexperiencemovieapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _feedbacks = MutableLiveData<List<Feedback>>()
    val feedbacks: LiveData<List<Feedback>> = _feedbacks

    fun loadFeedbacks(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getFeedbacks(movieId)
                _feedbacks.postValue(result)
            } catch (e: Exception) {
                _feedbacks.postValue(emptyList())
            }
        }
    }
}
