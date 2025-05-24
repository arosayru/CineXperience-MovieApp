package com.adithya.cinexperiencemovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adithya.cinexperiencemovieapp.R

class GenreAdapter(private var genres: List<String>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreText: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_category_chip, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.genreText.text = genres[position]
    }

    override fun getItemCount(): Int = genres.size

    fun updateData(newGenres: List<String>) {
        genres = newGenres
        notifyDataSetChanged()
    }
}
