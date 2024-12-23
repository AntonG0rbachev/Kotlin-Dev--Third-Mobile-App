package com.example.list_4pm2_2425.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.list_4pm2_2425.R
import com.example.list_4pm2_2425.data.Student
import com.example.list_4pm2_2425.databinding.FragmentStudentInfoBinding
import com.example.list_4pm2_2425.repository.AppRepository
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "student_param"

class StudentInfoFragment : Fragment() {
    private lateinit var student: Student
    private lateinit var _binding: FragmentStudentInfoBinding
    val binding
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getLong(ARG_PARAM1)
            val groupId = it.getLong("GROUP")
            Log.e("PARAM", "PARAMETER ${param1}")
            if (param1 == 0L) {
                student = Student().apply { groupID = groupId }
            }
            else {
                student = AppRepository.getInstance().getStudent(param1)
                Log.e("THIRD GROUP", "STUDENT GROUP ${student.groupID}")
            }
        }
    }

    companion object {
        fun newInstance(student: Student) =
            StudentInfoFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, student.id)
                    putLong("GROUP", student.groupID!!)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentStudentInfoBinding.inflate(inflater, container, false)
        val sexArray = resources.getStringArray(R.array.SEX)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexArray)
        binding.spinner2.adapter = adapter
        binding.spinner2.setSelection(student.sex)
        binding.spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                student.sex = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            student.birthDate.time = SimpleDateFormat("yyyy.MM.dd").parse("$year.$month.$dayOfMonth")?.time ?: student.birthDate.time
        }
        binding.etFirstname.setText(student.firstName)
        binding.etLastname.setText(student.lastName)
        binding.etMiddlename.setText(student.middleName)
        binding.etPhone.setText(student.phone)
        binding.calendarView.date = student.birthDate.time
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            if(binding.etLastname.text.toString().isNotEmpty() &&
                binding.etFirstname.text.toString().isNotEmpty() &&
                binding.etMiddlename.text.toString().isNotEmpty()
            ){
                Log.e("ADD", "ENTERED IF")
                student.lastName = binding.etLastname.text.toString()
                student.firstName = binding.etFirstname.text.toString()
                student.middleName = binding.etMiddlename.text.toString()
                student.phone = binding.etPhone.text.toString()
                AppRepository.getInstance().updateStudent(student)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        return binding.root

        //return inflater.inflate(R.layout.fragment_student_info, container, false)
    }
}