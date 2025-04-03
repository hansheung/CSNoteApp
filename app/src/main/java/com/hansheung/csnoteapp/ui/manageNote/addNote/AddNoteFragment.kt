package com.hansheung.csnoteapp.ui.manageNote.addNote

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.ui.HomeFragmentDirections
import com.hansheung.csnoteapp.ui.NotesIntent
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteFragment
import com.hansheung.note_taking.ui.addNote.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment : BaseManageNoteFragment() {

    override val viewModel: AddNoteViewModel by viewModels()

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Add Note"

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            val note = Note(title = title, desc = desc, color = selectedColor)

            viewModel.handleIntent(com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent.SubmitNote(note))

            lifecycleScope.launch {
                viewModel.finish.collect{
                    findNavController().popBackStack()
                }
            }
        }
    }
}