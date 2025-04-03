package com.hansheung.csnoteapp.ui.manageNote.base

import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel

abstract class BaseManageNoteViewModel(
    authService: AuthService
) : BaseViewModel(authService) {

    abstract fun handleIntent(intent: NotesIntent)
}

sealed class NotesIntent {
    data class SubmitNote(val note: Note) : NotesIntent()
    data class GetNote(val noteId: String) : NotesIntent()
}