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
class UpdateActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    var CurrentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update) // Ensure this matches the correct XML file

        supportActionBar?.title="UpdateActivity"

        // Initialize Views before using them
        etTitle = findViewById(R.id.et_TitleUpdate)
        etDescription = findViewById(R.id.et_DescUpdate)
        btnSave = findViewById(R.id.btn_saveUpdate)
        btnCancel = findViewById(R.id.btn_cancleUpdate)

        getAndsetData() // Now safe to call



        btnSave.setOnClickListener {
           updateNotes()
        }

        btnCancel.setOnClickListener {
            Toast.makeText(this, "Update Canceled", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateNotes() {
        val updatedTitle = etTitle.text.toString()
        val updatedDescription = etDescription.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle", updatedTitle)
        intent.putExtra("updatedDescription", updatedDescription)

        if(CurrentId != -1){
            intent.putExtra("noteId", CurrentId)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun getAndsetData() {

        //set
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")

         CurrentId=intent.getIntExtra("currentId",-1)

        //get
        etTitle.setText(currentTitle)
        etDescription.setText(currentDescription)
    }


}
