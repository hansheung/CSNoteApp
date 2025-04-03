package com.hansheung.csnoteapp.ui.adapter

import android.graphics.Color
import com.hansheung.csnoteapp.data.model.Note
import com.hansheung.csnoteapp.databinding.ItemLayoutNoteBinding
import com.hansheung.mob22_mvi.core.crop
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter(
    private var notes: List<Note>,
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutNoteBinding.inflate(inflater,parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    fun setNotes(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }

    inner class NoteViewHolder(
        private val binding: ItemLayoutNoteBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(note: Note): Boolean{

            binding.run{
                tvTitle.text = note.title.crop(10)
                tvDesc.text = note.desc.crop(20)
                cvNote.setCardBackgroundColor(Color.parseColor(note.color))
            }

            binding.cvNote.setOnClickListener{
                clickListener?.onClickItem(note)
            }

            binding.cvNote.setOnLongClickListener {
                clickListener?.onLongClickItem(note)
                return@setOnLongClickListener true
            }

            return true
        }

    }

    interface ClickListener {
        fun onClickItem(item: Note)
        fun onLongClickItem(item: Note)
    }
}