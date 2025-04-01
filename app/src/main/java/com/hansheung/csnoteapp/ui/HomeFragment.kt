package com.hansheung.csnoteapp.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.databinding.FragmentHomeBinding
import com.hansheung.csnoteapp.ui.adapter.NoteAdapter
import com.hansheung.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override val viewModel:HomeViewModel by viewModels()

    private lateinit var adapter: NoteAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarLayout = requireActivity().findViewById<LinearLayout>(R.id.toolbarLayout)
        toolBarLayout.visibility = View.VISIBLE

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "My Notes"

        val tvLogout = requireActivity().findViewById<TextView>(R.id.tvLogout)
        tvLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(HomeFragmentDirections.actionToLoginFragment())
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupAdapter()

        viewModel.handleIntent(NotesIntent.LoadNotes)

        binding.fabAdd.setOnClickListener{
            val randomNote = generateRandomNote()
            viewModel.handleIntent(NotesIntent.AddNote(randomNote))
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.state.collect{ state ->
                adapter.setNotes(state.notes)
            }
        }
    }


    private fun setupAdapter(){
        adapter = NoteAdapter(emptyList())
        binding.rvNotes.adapter = adapter
        binding.rvNotes.layoutManager = GridLayoutManager(requireContext(),2)

//        adapter.setClickListener(object: NoteAdapter.ClickListener{
//            override fun onClickItem(item: Note) {
//                val action = HomeFragmentDirections.actionHomeToEditNote(item.id!!)
//                findNavController().navigate(action)
//            }
//
//            override fun onLongClickItem(item: Note) {
//                BottomSheetFragment(item.id!!).show(parentFragmentManager, "Bottom Sheet Dialog")
//            }
//
//        })
    }

    private fun generateRandomNote(): Note {
        val titles = listOf("Work", "Grocery", "Reminder", "Study", "Idea")
        val contents = listOf(
            "Lorem ipsum dolor sit amet",
            "Finish homework before 5pm",
            "Buy milk, eggs, and bread",
            "Prepare for meeting tomorrow",
            "Remember to stretch!"
        )
        val colors = listOf("#FFCDD2", "#C8E6C9", "#BBDEFB", "#FFF9C4", "#D1C4E9")

        val randomTitle = titles.random()
        val randomContent = contents.random()
        val randomColor = colors.random()

        return Note(
            title = "$randomTitle Note",
            desc = randomContent,
            color = randomColor
        )
    }

}