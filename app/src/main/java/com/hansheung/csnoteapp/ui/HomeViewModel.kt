package com.hansheung.csnoteapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.data.repo.NotesRepo
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    authService: AuthService,
    private val repo: NotesRepo
) : BaseViewModel(authService) {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private val _empty = MutableStateFlow(true)
    val empty: StateFlow<Boolean> = _empty

    fun handleIntent(intent: NotesIntent){
        when(intent){
            NotesIntent.LoadNotes -> loadNotes()
            is NotesIntent.DeleteNote -> delete(intent.noteId)
        }
    }

    private fun loadNotes() {
        viewModelScope.launch (Dispatchers.IO){
            _state.value = _state.value.copy(isLoading = true)
            repo.getNotes().collect { notes ->
                _state.value = NotesState(notes = notes)
                _empty.update{ notes.isEmpty() }
            }
        }
    }

    fun delete(Id:String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(Id)
        }
    }

}

sealed class NotesIntent {
    object LoadNotes : NotesIntent()
    data class DeleteNote(val noteId: String) : NotesIntent()
}

data class NotesState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String? = null
)
