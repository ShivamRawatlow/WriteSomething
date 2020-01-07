package com.thelegendofawizard.writesomething.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelegendofawizard.writesomething.MyNote
import com.thelegendofawizard.writesomething.Repository

class HomeViewModel(val application: Application, val repository: Repository) : ViewModel() {

    //val myEmail = repository.getFirebaseAuthInstance().currentUser?.email.toString()
    val myName = repository.getFirebaseAuthInstance().currentUser?.displayName.toString()
    val notesList = MutableLiveData<List<MyNote>>()

    init {
        getNotes()
    }

    fun getNotes(){

            repository.getAllNotes()
                .whereEqualTo("visibility",true)
                .addSnapshotListener { snapshots, firestoreException ->
                    val tempPersonNoteList = mutableListOf<MyNote>()
                    if (firestoreException != null) {
                        return@addSnapshotListener
                    }
                    snapshots?.let {
                        for (doc in snapshots) {

                            val id = doc.getString("id")!!
                            val email = doc.getString("email")!!
                            val name = doc.getString("name")!!
                            val title = doc.getString("title")
                            val body = doc.getString("body")
                            val visibility = doc.getBoolean("visibility")!!
                            val note = MyNote(id, email, name, title, body, visibility)
                            tempPersonNoteList.add(note)
                        }
                    }
                    notesList.value = tempPersonNoteList
                }
        }

    }

