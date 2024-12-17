package com.example.list_4pm2_2425.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.list_4pm2_2425.R
import com.example.list_4pm2_2425.data.Student
import com.example.list_4pm2_2425.databinding.FragmentStudentInfoBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val ARG_PARAM1 = "student_param"

class StudentInfoFragment : Fragment() {
    private lateinit var student: Student
    private lateinit var _binding: FragmentStudentInfoBinding
    val binding
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if (param1 == null) {
                student = Student()
            }
            else {
                val paramType = object: TypeToken<Student>() {}.type
                student = Gson().fromJson<Student>(param1, paramType)
            }
        }
    }

    companion object {
        fun newInstance(student: Student) =
            StudentInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(student))
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sexArray = resources.getStringArray(R)
        binding.spinner2.onItemSelectedListener = object :
        return inflater.inflate(R.layout.fragment_student_info, container, false)
    }
}