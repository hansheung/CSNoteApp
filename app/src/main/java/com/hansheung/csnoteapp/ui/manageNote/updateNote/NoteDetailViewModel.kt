package com.hansheung.csnoteapp.ui.manageNote.updateNote

import androidx.lifecycle.viewModelScope
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.data.repo.NotesRepo
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteState
import com.hansheung.csnoteapp.ui.manageNote.base.BaseManageNoteViewModel
import com.hansheung.csnoteapp.ui.manageNote.base.NotesIntent
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repo: NotesRepo,
    authService: AuthService,
) : BaseManageNoteViewModel(authService) {

    private val _state = MutableStateFlow(BaseManageNoteState())
    val state = _state.asStateFlow()

    override fun handleIntent(intent: NotesIntent) {
        when (intent) {
            is NotesIntent.GetNote -> getNote(intent.noteId)
            else -> Unit
        }
    }

    private fun getNote(noteId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val note = repo.getNote(noteId)
            _state.value = _state.value.copy(
                isLoading = false,
                note = if (note != null) note else Note()
            )
        }
    }
}