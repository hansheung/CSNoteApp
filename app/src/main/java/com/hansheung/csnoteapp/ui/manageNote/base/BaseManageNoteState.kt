package com.hansheung.csnoteapp.ui.manageNote.base

import com.hansheung.csnoteapp.data.model.Note

data class BaseManageNoteState(
    val isLoading: Boolean = false,
    val note: Note = Note(),
    val error: String? = null
)
