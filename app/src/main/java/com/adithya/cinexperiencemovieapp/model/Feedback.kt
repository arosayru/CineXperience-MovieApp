package com.adithya.cinexperiencemovieapp.model

import java.util.Date

data class Feedback(
    val id: String,
    val username: String,
    val comment: String,
    val date: String? = null,
    val rating: Double? = null
)
