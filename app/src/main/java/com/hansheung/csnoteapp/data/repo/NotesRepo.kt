package com.hansheung.csnoteapp.data.repo

import com.hansheung.csnoteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun getNote(id: String): Note?
    suspend fun deleteNote(id: String)
    suspend fun updateNote(note: Note)
}