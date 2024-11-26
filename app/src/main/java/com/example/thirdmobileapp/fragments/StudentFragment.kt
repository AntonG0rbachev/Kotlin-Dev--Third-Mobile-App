package ru.chernov.listapp.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.chernov.listapp.R
import ru.chernov.listapp.data.Group
import ru.chernov.listapp.databinding.FragmentStudentBinding
import ru.chernov.listapp.view_models.StudentViewModel

class StudentFragment : Fragment() {

    companion object {
        private  lateinit var  group: Group
        fun newInstance(group: Group): StudentFragment{
            this.group = group
            return StudentFragment()
        }
    }

    private val viewModel: StudentViewModel by viewModels()



    private lateinit var _binding: FragmentStudentBinding
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
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        binding.rvStudents.layoutManager=

        return inflater.inflate(R.layout.fragment_student, container, false)
    }
}