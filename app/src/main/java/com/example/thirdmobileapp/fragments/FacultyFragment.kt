package com.example.thirdmobileapp.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdmobileapp.R
import com.example.thirdmobileapp.view_models.FacultyViewModel

class FacultyFragment : Fragment() {

    companion object {
        fun newInstance() = FacultyFragment()
    }

    private val viewModel: FacultyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_faculty, container, false)
    }
}