package com.example.notesapplication


import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUpdateBtn: Button
    private var noteID: String? = null
    lateinit var viewModel: NoteViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdit = findViewById(R.id.ideditNoteDescription)
        addUpdateBtn = findViewById(R.id.idBtnAddUpdate)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getStringExtra("noteID")
            addUpdateBtn.text = "Update Note"
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)
        } else {
            addUpdateBtn.setText("Save Note")
        }
        addUpdateBtn.setOnClickListener {
            createNote()
        }
    }

    private fun createNote() {
        val noteType = intent.getStringExtra("noteType")
        val noteTitle = noteTitleEdt.text.toString()
        val noteDescription = noteDescriptionEdit.text.toString()
        if (noteTitle.isEmpty() || noteDescription.isEmpty())
            return

        val calendar = Calendar.getInstance()
        val currentDate: String = Utils.getFormattedTime(calendar)

        if (noteType.equals("Edit")) {

            val updateNote = Note(
                noteTitle = noteTitle,
                noteDescription = noteDescription,
                timeStamp = currentDate,
                id = noteID.toString()
            )

            val db = Firebase.firestore
            val doc = db.collection("notes").document(noteID.toString())
            doc.set(Note(noteTitle, noteDescription, currentDate, doc.id)).addOnSuccessListener {
                viewModel.updateNote(updateNote)
            }
            Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
            finish()
        } else {
            val db = Firebase.firestore
            val doc = db.collection("notes").document()
            doc.set(Note(noteTitle, noteDescription, currentDate, doc.id)).addOnSuccessListener {
                viewModel.addNote(
                    Note(
                        noteTitle = noteTitle,
                        noteDescription = noteDescription, timeStamp = currentDate,
                        id = doc.id
                    )
                )
                finish()
            }
            Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        createNote()
    }
}