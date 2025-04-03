package com.hansheung.csnoteapp.ui.manageNote.editNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansheung.csnoteapp.R
import com.hansheung.csnoteapp.databinding.FragmentEditNoteBinding


class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

}