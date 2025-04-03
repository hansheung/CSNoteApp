package com.hansheung.csnoteapp.ui.manageNote.addNote

import androidx.fragment.app.viewModels
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteFragment
import com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent
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

            viewModel.handleIntent(NotesIntent.SubmitNote(note))

            lifecycleScope.launch {
                viewModel.finish.collect{
                    findNavController().popBackStack()
                }
            }
        }
    }
}