package com.example.notesapplication


import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUpdateBtn: Button
    var noteID = -1
    lateinit var viewModel : NoteViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdit = findViewById(R.id.ideditNoteDescription)
        addUpdateBtn = findViewById(R.id.idBtnAddUpdate)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel :: class.java]
        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            addUpdateBtn.text = "Update Note"
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)
        }
        else{
            addUpdateBtn.setText("Save Note")
        }
        addUpdateBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()
            if(noteTitle.isEmpty() || noteDescription.isEmpty())
                return@setOnClickListener

            val calendar = Calendar.getInstance()
            val currentDate : String = Utils.getFormattedTime(calendar)

            if(noteType.equals("Edit")){
                    val updateNote = Note(noteTitle , noteDescription , currentDate)
                    updateNote.id  = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated", Toast.LENGTH_LONG).show()
            }else {
                viewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                Toast.makeText(this,"Note Added",Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

}