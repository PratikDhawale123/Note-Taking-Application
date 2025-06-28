package com.example.notetakingapplication.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapplication.Adapter.NoteAdapter
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.NoteApplication
import com.example.notetakingapplication.R
import com.example.notetakingapplication.ViewModel.NoteViewModel
import com.example.notetakingapplication.ViewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        registerActivityResultLauncher()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = NoteAdapter(this)
        recyclerView.adapter = myAdapter

        val viewModelFactory = ViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.MyAllNotes.observe(this, Observer { notes ->
            // Update UI
            myAdapter.updateNotes(notes) // Use updateNotes instead of setNotes
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(myAdapter.getNote(viewHolder.adapterPosition))

            }

        }).attachToRecyclerView(recyclerView)
    }

    private fun registerActivityResultLauncher() {
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result ->
                val resultCode = result.resultCode
                val data= result.data

                if (resultCode== RESULT_OK && data!= null){
                    val noteTitle :String=data.getStringExtra("title").toString()
                    val noteDescription=data.getStringExtra("Description").toString()

                    val note= Note(noteTitle,noteDescription)
                    noteViewModel.insert(note)
                }

            })

        updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {resultupdate ->
                val resultCode = resultupdate.resultCode
                val data= resultupdate.data

                if (resultCode== RESULT_OK && data!= null){

                    val updatedTitle:String = data.getStringExtra("updatedTitle").toString()

                    val updatedDesc:String = data.getStringExtra("updatedDescription").toString()

                    val noteId=data.getIntExtra("noteId",-1)

                    val newNote =  Note(updatedTitle,updatedDesc)
                    newNote.id = noteId

                    noteViewModel.update(newNote)
                }

            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
                R.id.itemAddNotes -> {
                    val intent=Intent(this,NoteAddActivity::class.java)
                    addActivityResultLauncher.launch(intent)
                }
            R.id.itemDeleteNotes ->
                ShowDialog()
        }
        return true
    }

    private fun ShowDialog() {

        val dialogMessage=AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes ")
        dialogMessage.setMessage("If you want to delete all Notes click YES and if u want to delete Specific Notes swipe left or right ")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

            noteViewModel.deleteAllNotes()
        }).create().show()

    }

}