package com.example.list_4pm2_2425.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.list_4pm2_2425.R
import com.example.list_4pm2_2425.view_models.StudentsViewModel
import com.example.list_4pm2_2425.data.Group
import com.example.list_4pm2_2425.databinding.FragmentStudentsBinding

class StudentsFragment : Fragment() {

    companion object {
        private lateinit var group: Group
        fun newInstance(group: Group): StudentsFragment{
            this.group=group
            return StudentsFragment()
        }
    }

    private lateinit var viewModel: StudentsViewModel

    private lateinit var _binding: FragmentStudentsBinding
    val binding
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_students, container, false)
    }
}