package com.hansheung.note_taking.ui.addNote

import androidx.lifecycle.viewModelScope
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.data.repo.NotesRepo
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteViewModel
import com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent
import com.hansheung.mob21firebase.core.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel@Inject constructor(
    private val repo: NotesRepo,
    authService: AuthService
) : BaseManageNoteViewModel(authService) {

    override fun handleIntent(intent: NotesIntent){
        when(intent){
            is NotesIntent.SubmitNote -> addNote(intent.note)
            else -> Unit
        }
    }

    private fun addNote(note: Note) {
        viewModelScope.launch {
            errorHandler {
                require(note.title.isNotEmpty()){"Title cannot be empty"}
                require(note.desc.isNotEmpty()){"Description cannot be empty"}

                repo.addNote(note)
                _finish.emit(Unit)
            }
        }
    }

}


