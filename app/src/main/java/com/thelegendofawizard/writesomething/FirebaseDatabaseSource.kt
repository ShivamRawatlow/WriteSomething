package com.thelegendofawizard.writesomething
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseDatabaseSource {
    private val database : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getAllMembers(): CollectionReference {
        return database.collection("Members")
    }

    fun getMember(email: String): DocumentReference {
        return database.collection("Members").document(email)
    }

    fun getNotesOfMember(email: String): Query {
        return database.collection("Notes")
            .whereEqualTo("email",email)
    }

    fun getAllNotes(): CollectionReference {
        return return database.collection("Notes")
    }

    fun saveUser(personDetail: PersonDetail): Task<Void> {
        return database.collection("Members").document(personDetail.email)
            .set(personDetail)
    }

    fun saveNote(myNote:MyNote): Task<Void> {
        return database.collection("Notes")
            .document(myNote.id)
            .set(myNote)// completely overwrite the old data
    }

    fun deleteNote(myNote:MyNote): Task<Void> {
        return database.collection("Notes")
            .document(myNote.id)
            .delete()
    }

    fun updateMemberDetail(email:String,type:String, value:String): Task<Void> {
        return database.collection("Members").document(email)
            .update(type,value)
    }


}