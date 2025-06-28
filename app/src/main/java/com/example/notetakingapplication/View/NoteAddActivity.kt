package com.example.notetakingapplication.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notetakingapplication.R

class NoteAddActivity : AppCompatActivity() {

     lateinit var etTitle:EditText
     lateinit var etDescription:EditText
     lateinit var btn_Save:Button
     lateinit var btn_Cancle:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_add)
        supportActionBar?.title="Add Note"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        etTitle=findViewById(R.id.et_Title)
        etDescription=findViewById(R.id.et_Desc)
        btn_Cancle=findViewById(R.id.btn_cancle)
        btn_Save=findViewById(R.id.btn_save)

        btn_Cancle.setOnClickListener {
            Toast.makeText(applicationContext,"Nothing Saved",Toast.LENGTH_SHORT).show()
            finish()
        }

        btn_Save.setOnClickListener {
            saveNotes()
        }

    }

    private fun saveNotes() {
        val noteTitle=etTitle.text.toString()
        val noteDescription=etDescription.text.toString()

        val intent=Intent()
        intent.putExtra("title",noteTitle)
        intent.putExtra("Description",noteDescription)
        setResult(RESULT_OK,intent)
        finish()
    }
}