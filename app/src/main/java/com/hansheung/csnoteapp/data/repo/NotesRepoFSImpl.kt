package com.hansheung.csnoteapp.data.repo

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansheung.csnoteapp.core.CustomException
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.mob21firebase.core.service.AuthService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NotesRepoFSImpl(

    private val db: FirebaseFirestore= Firebase.firestore,
    private val authService: AuthService

): NotesRepo {

    private fun getCollectionRef(): CollectionReference {
        val uid = authService.getUid() ?: throw CustomException("No valid user found")
        return db.collection("users/$uid/notes")
    }

    override suspend fun getNotes() = callbackFlow {
        val listener = getCollectionRef().addSnapshotListener{value, error->
            if(error!=null){
                trySend(emptyList())
                return@addSnapshotListener
            }

            val notes = mutableListOf<Note>()

            value?.documents?.forEach{doc ->
                val note = doc.toObject(Note::class.java)
                if(note!=null){
                    notes.add(note.copy(id=doc.id))
                }
            }
            trySend(notes)
        }
        awaitClose{
            listener.remove()
        }
    }

    override suspend fun addNote(note: Note) {
        val ref = getCollectionRef().document()
        ref.set(note.copy(id = ref.id)).await()
    }

    override suspend fun getNote(id: String): Note? {
        val snapshot = getCollectionRef().document(id).get().await()
        return snapshot.toObject(Note::class.java)
    }

    override suspend fun deleteNote(id: String) {
        getCollectionRef().document(id).delete().await()
    }

    override suspend fun updateNote(note: Note) {
        Log.d("debugging", note.toString())
        getCollectionRef().document(note.id).set(note).await()
    }
}