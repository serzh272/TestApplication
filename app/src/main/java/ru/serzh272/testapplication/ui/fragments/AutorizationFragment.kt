package ru.serzh272.testapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.serzh272.testapplication.databinding.FragmentAutorizationBinding

class AutorizationFragment : Fragment() {
    lateinit var binding:FragmentAutorizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAutorizationBinding.inflate(inflater)
        return binding.root
    }
}