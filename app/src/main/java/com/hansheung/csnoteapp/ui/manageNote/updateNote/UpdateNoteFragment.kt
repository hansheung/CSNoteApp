package com.hansheung.csnoteapp.ui.manageNote.updateNote

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteFragment
import com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateNoteFragment : BaseManageNoteFragment() {

    override val viewModel: UpdateNoteViewModel by viewModels()
    private val args:  UpdateNoteFragmentArgs by navArgs()

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Update Note"

        binding.btnSubmit.text = "Update"

        viewModel.handleIntent(NotesIntent.GetNote(args.id))

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            val note = viewModel.state.value.note.copy(title = title, desc = desc, color = selectedColor)

            viewModel.handleIntent(NotesIntent.SubmitNote(note))

            lifecycleScope.launch {
                viewModel.finish.collect{
                    val action = UpdateNoteFragmentDirections.actionUpdateToHome()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                val note = state.note
                Log.d("debugging", note.toString())

                binding.etTitle.text = Editable.Factory.getInstance().newEditable(note.title)
                binding.etDesc.text = Editable.Factory.getInstance().newEditable(note.desc)

                val colorBoxes = listOf(binding.colorBox1,binding.colorBox2,binding.colorBox3,binding.colorBox4,binding.colorBox5)

                val noteColor = state.note.color.uppercase()

                // Reset all selections
                colorBoxes.forEach { it.isSelected = false }

                val boxToSelect = when (noteColor) {
                    "#00FF00" -> binding.colorBox1 // green
                    "#00FFFF" -> binding.colorBox2 // cyan
                    "#FF0000" -> binding.colorBox3 // red
                    "#800080" -> binding.colorBox4 // purple
                    "#FFD700" -> binding.colorBox5 // yellow
                    else -> null
                }

                boxToSelect?.isSelected = true
                selectedColorBox = boxToSelect
                selectedColor = noteColor

            }
        }
    }
}