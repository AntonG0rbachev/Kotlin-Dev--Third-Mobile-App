package com.example.thirdmobileapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdmobileapp.MainActivity
import com.example.thirdmobileapp.R
import ru.chernov.listapp.data.Faculty
import ru.chernov.listapp.databinding.FragmentFacultyBinding
import ru.chernov.listapp.view_models.FacultyViewModel

class FacultyFragment : Fragment(), MainActivity.Edit {

    companion object {
        //fun newInstance() = FacultyFragment()
        private var INSTANCE: FacultyFragment? = null
        fun getInstance(): FacultyFragment {
            if (INSTANCE == null)  INSTANCE = FacultyFragment()
            return INSTANCE ?: throw Exception("FacultyFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyViewModel
    private lateinit var _binding : FragmentFacultyBinding
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        binding.rvFaculty.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner) {
            binding.rvFaculty.adapter = FacultyAdapter(it?.items!!)
        }
    }

    private inner class FacultyAdapter(private val items: List<Faculty>):RecyclerView.Adapter<FacultyAdapter.ItemHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_faculty_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(items[position])
        }

        private var lastView: View ?= null
        private fun updateCurrentView(view: View) {
            lastView?.findViewById<ConstraintLayout>(R.id.clFacultyElement)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
            lastView?.findViewById<TextView>(R.id.tvFaculty)?.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
            view.findViewById<ConstraintLayout>(R.id.clFacultyElement).setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.blue)
            )
            view.findViewById<TextView>(R.id.tvFaculty).setTextColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
            lastView = view
        }

        private inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
            private lateinit var faculty : Faculty

            fun bind(faculty: Faculty){
                this.faculty = faculty
                val tv = itemView.findViewById<TextView>(R.id.tvFaculty)
                tv.text = faculty.name

                if (faculty == viewModel.faculty)
                    updateCurrentView(itemView)

//                tv.setOnClickListener {
//                    viewModel.setCurrentFaculty(faculty)
//                    updateCurrentView(itemView)
//                }

                itemView.findViewById<ConstraintLayout>(R.id.clFacultyElement).setOnClickListener {
                    viewModel.setCurrentFaculty(faculty)
                    updateCurrentView(itemView)
                }
            }
        }
    }

    override fun add() {
        editFaculty()
    }

    override fun update() {
        editFaculty(viewModel.faculty?.name ?: "")
    }

    override fun delete() {
        deleteDialog()
    }

    private fun deleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить факультет ${viewModel.faculty?.name ?: ""}?")
            .setPositiveButton("Да") { _,_ ->
                viewModel.deleteFaculty()
            }
            .setNegativeButton("Net", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun editFaculty(facultyName: String="") {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_strting, null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(facultyName)
        messageText.text = "Укажите название факультета"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("Подтверждаю") { _, _ ->
                if (inputString.text.isNotBlank()) {
                    if(facultyName.isBlank())
                        viewModel.appendFaculty(inputString.text.toString())
                    else
                        viewModel.updateFaculty(inputString.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

}