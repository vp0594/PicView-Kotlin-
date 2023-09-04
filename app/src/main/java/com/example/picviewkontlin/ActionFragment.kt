package com.example.picviewkontlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.picviewkontlin.databinding.FragmentActionBinding

class ActionFragment : Fragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentActionBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_action, container, false)
        binding = FragmentActionBinding.bind(view)
        return binding.root
    }
}