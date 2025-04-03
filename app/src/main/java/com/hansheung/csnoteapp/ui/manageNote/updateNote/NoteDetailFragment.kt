package com.hansheung.csnoteapp.ui.manageNote.updateNote

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.databinding.FragmentNoteDetailBinding
import com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent
import com.hansheung.mob21firebase.ui.base.BaseFragment
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment: BaseFragment() {

    protected lateinit var binding: FragmentNoteDetailBinding
    override val viewModel: NoteDetailViewModel by viewModels()
    private val args:  NoteDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Note Details"

        viewModel.handleIntent(NotesIntent.GetNote(args.id))

        binding.btnEdit.setOnClickListener {
            val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToUpdateNoteFragment(args.id)
            findNavController().navigate(action)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                val note = state.note

                binding.etTitle.text = note.title
                binding.etDesc.text = note.desc

                if (note.color.isNotBlank()) {
                    binding.root.setBackgroundColor(Color.parseColor(note.color))
                }
            }
        }
    }
}