package com.hansheung.csnoteapp.ui.manageNote.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.databinding.FragmentManageNoteBinding
import com.hansheung.csnoteapp.ui.HomeFragmentDirections
import com.hansheung.csnoteapp.ui.manageNote.addNote.AddNoteFragmentDirections
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.note_taking.ui.addNote.NotesIntent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


abstract class BaseManageNoteFragment: BaseFragment() {
    protected lateinit var binding: FragmentManageNoteBinding
    abstract override val viewModel: BaseManageNoteViewModel
    private var selectedColorBox: View? = null
    private var selectedColor: String = "#FFFFFF"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        // Set background drawables
        binding.colorBox1.setBackgroundResource(R.drawable.color_box_green)
        binding.colorBox2.setBackgroundResource(R.drawable.color_box_cyan)
        binding.colorBox3.setBackgroundResource(R.drawable.color_box_red)
        binding.colorBox4.setBackgroundResource(R.drawable.color_box_purple)
        binding.colorBox5.setBackgroundResource(R.drawable.color_box_yellow)

        val colorBoxes = listOf(
            binding.colorBox1,
            binding.colorBox2,
            binding.colorBox3,
            binding.colorBox4,
            binding.colorBox5
        )

        colorBoxes.forEach { box ->
            box.setOnClickListener { view ->

                selectedColorBox?.isSelected = false

                view.isSelected = true
                selectedColorBox = view

                val color = when(view.id) {
                    R.id.colorBox1 -> R.color.green
                    R.id.colorBox2 -> R.color.cyan
                    R.id.colorBox3 -> R.color.red
                    R.id.colorBox4 -> R.color.purple
                    R.id.colorBox5 -> R.color.yellow
                    else -> R.color.green
                }

                val colorInt = ContextCompat.getColor(requireContext(), color)
                selectedColor = String.format("#%06X", 0xFFFFFF and colorInt)

            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            val note = Note(title = title, desc = desc, color = selectedColor)
            viewModel.handleIntent(NotesIntent.AddNote(note))

            //viewModel.handleIntent(Note(title=title, desc = desc, color=selectedColor))

            lifecycleScope.launch {
                viewModel.finish.collect{

                    findNavController().popBackStack()
                }
            }
        }
    }

}