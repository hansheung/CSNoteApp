package com.hansheung.csnoteapp.ui.manageNote

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansheung.csnoteapp.databinding.FragmentBottomSheetBinding
import com.hansheung.csnoteapp.ui.HomeViewModel
import com.hansheung.csnoteapp.ui.manageNote.note.NoteFragmentDirections
import com.hansheung.csnoteapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment(
    private val id: String
): BottomSheetDialogFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editOption.setOnClickListener {
            val action = NoteFragmentDirections.actionNoteFragmentToEditNoteFragment(id)
            findNavController().navigate(action)
            dialog?.dismiss()
        }

        binding.deleteOption.setOnClickListener {
            showDeleteDialogBox(id)
            //dialog!!.dismiss() //If dismiss here, the bottom sheet already been dismissed.
        }
    }

    private fun showDeleteDialogBox(id: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_delete_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnDelete = dialog.findViewById<Button>(R.id.btnDelete)

        btnCancel.setOnClickListener {
            dialog.dismiss()
            this.dismiss()
        }

        btnDelete.setOnClickListener {
            viewModel.delete(id)
            dialog.dismiss() //Dismissing the child which is the dialog box
            this.dismiss() // Dismissing the parent which is the Bottom Sheet Fragment
        }
        dialog.show()
    }
}