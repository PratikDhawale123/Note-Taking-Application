package com.example.notetakingapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository):ViewModel() {

    // we created the function to retrieve all the notes from Repository

    //we will put the list of notes under supervision of LiveData<> , and as we have used Flow<> for other list<Note> , we will suspend the Flow<> and instead use LiveData<> therefore use .asLiveData()
    //Why asLiveData?: Room supports Flow, but UI prefers LiveData. asLiveData converts the Flow into LiveData for the UI to observe.
    val MyAllNotes : LiveData<List<Note>> = repository.MyAllNotes.asLiveData()

    //andaroid-x provides viewmodelscope so instead of using coroutine scop here we will use viewmodelscope
    //Why Dispatchers.IO?: Database operations are performed in the background thread (IO), not on the main thread, to keep the app responsive.
    fun insert(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun update(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }

    fun delete(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.delete(note)

    }
    fun deleteAllNotes() =viewModelScope.launch (Dispatchers.IO){
        repository.deleteAllNotes()

    }

    //now we will show the referene of this viewmodel in our main activity


}

//create method checks if the requested ViewModel class matches NoteViewModel.
//If yes, it creates an instance of NoteViewModel with the repository.
//If no, it throws an exception because the requested ViewModel type isn’t supported.
class ViewModelFactory(private var repository: NoteRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}