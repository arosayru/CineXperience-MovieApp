package com.adithya.cinexperiencemovieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adithya.cinexperiencemovieapp.model.Feedback
import com.adithya.cinexperiencemovieapp.model.FeedbackItem
import com.adithya.cinexperiencemovieapp.repository.MovieRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _feedbacks = MutableLiveData<List<Feedback>>()
    val feedbacks: LiveData<List<Feedback>> = _feedbacks

    fun loadFeedbacks(movieId: Int) {
        viewModelScope.launch {
            try {
                val feedbackItems: List<FeedbackItem> = repository.getFeedbacks(movieId)

                val mapped = feedbackItems.map { item ->
                    Feedback(
                        id = item.id,
                        username = item.author,
                        comment = item.content,
                        date = extractDate(item),
                        rating = item.authorDetails?.rating ?: 0.0
                    )
                }

                _feedbacks.postValue(mapped)
            } catch (e: Exception) {
                _feedbacks.postValue(emptyList())
            }
        }
    }

    private fun extractDate(item: FeedbackItem): String {
        val rawDate = item.createdAt ?: return ""
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = inputFormat.parse(rawDate)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date!!)
        } catch (e: Exception) {
            ""
        }
    }
}
