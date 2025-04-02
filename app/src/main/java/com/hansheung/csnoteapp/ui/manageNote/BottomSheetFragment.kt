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
            val action = EditNoteFragmentDirections.actionToUpdateNote(id)
            findNavController().navigate(action)
            dialog?.dismiss()
        }

        binding.deleteOption.setOnClickListener {
            showDeleteDialogBox(id)
            //dialog!!.dismiss() //If dismiss here, the bottom sheet already been dismissed.
        }
    }
}