package com.adithya.cinexperiencemovieapp.ui.about

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adithya.cinexperiencemovieapp.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val typingSpeed: Long = 30
    private val descriptionText = "CineXperience brings the magic of movies to your fingertips. Discover the latest releases, explore detailed movie information, watch trailers, and stay updated with what's trending. Whether you're a casual viewer or a cinephile, CineXperience is your go-to movie companion."

    private var index = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTypingAnimation()
    }

    private fun startTypingAnimation() {
        binding.aboutDescription.text = ""
        index = 0
        handler.post(object : Runnable {
            override fun run() {
                if (index <= descriptionText.length) {
                    binding.aboutDescription.text = descriptionText.substring(0, index)
                    index++
                    handler.postDelayed(this, typingSpeed)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}
