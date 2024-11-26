package com.example.thirdmobileapp.fragments

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.thirdmobileapp.MainActivity
import com.example.thirdmobileapp.R
import com.example.thirdmobileapp.data.Group
import com.example.thirdmobileapp.databinding.FragmentGroupBinding
import com.example.thirdmobileapp.view_models.GroupViewModel

class GroupFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: GroupFragment?= null

        fun getInstance(): GroupFragment {
            if( INSTANCE ==null)
                INSTANCE = GroupFragment()
            return INSTANCE ?: throw Exception("GroupFragment не создан")
        }
        fun newInstance(): GroupFragment {
            INSTANCE = GroupFragment()
            return INSTANCE!!
        }
    }

    private var tabPosition: Int = 0
    private lateinit var _binding: FragmentGroupBinding
    private val binding get() = _binding!!


    private val viewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    private  inner class GroupPageAdapter(fa:FragmentActivity, private val groups: List<Group>?)
        : FragmentStateAdapter(fa){
        override fun getItemCount(): Int{
            return (groups?.size ?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return StudentFragment.newInstance(groups!![position])
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.groupList.observe(viewLifecycleOwner){
            createUI(it)
        }
    }

    private fun createUI(groupList: List<Group>){
        binding.tlGroups.clearOnTabSelectedListeners()
        binding.tlGroups.removeAllTabs()

        for(i in 0 until (groupList.size)){
            binding.tlGroups.addTab(binding.tlGroups.newTab().apply {
                text = groupList.get(i).name
            })
        }
        val adapter =  GroupPageAdapter(requireActivity(), viewModel.groupList.value)
        binding.vpStudents.adapter = adapter
        TabLayoutMediator(binding.tlGroups, binding.vpStudents, true, true){
            tab,pos -> tab.text = groupList.get(pos).name
        }.attach()
        tabPosition = 0
        if(viewModel.group != null)
            tabPosition = if(viewModel.getGroupPosition >= 0)
                viewModel.getGroupPosition
            else
                0
        viewModel.setCurrentGroup(tabPosition)
        binding.tlGroups.selectTab(binding.tlGroups.getTabAt(tabPosition))
        binding.tlGroups.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentGroup(groupList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun add() {
        editGroup()
     }

    override fun update() {
        editGroup(viewModel.group?.name ?: "")
    }

    override fun delete() {
        deleteDialog()
        }


    private fun deleteDialog(){
        if(viewModel.group == null) return
        AlertDialog.Builder(requireContext())
            .setTitle("delete")
            .setMessage("hochesh ydalit? ${viewModel.group?.name ?: ""}")
            .setPositiveButton("Да"){
                _, _ -> viewModel.deleteGroups()
            }
            .setNegativeButton("No", null)
            .setCancelable(true)
            .create()
            .show()
    }


    private fun editGroup(groupName: String=""){
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_strting, null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(groupName)
        messageText.text = "Укажите название группы"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("Подтверждаю") { _, _ ->
                if (inputString.text.isNotBlank()) {
                    if(groupName.isBlank())
                        viewModel.appendGroup(inputString.text.toString())
                    else
                        viewModel.updateGroup(inputString.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

}