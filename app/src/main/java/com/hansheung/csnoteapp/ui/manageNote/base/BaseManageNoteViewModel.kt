package com.hansheung.csnoteapp.ui.manageNote.base

import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.note_taking.ui.addNote.NotesIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseManageNoteViewModel: BaseViewModel() {

    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    abstract fun handleIntent(intent: NotesIntent)

}