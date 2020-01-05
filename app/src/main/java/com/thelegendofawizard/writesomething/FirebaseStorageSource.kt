package com.thelegendofawizard.writesomething

import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FirebaseStorageSource {

   val storageInstance : FirebaseStorage by lazy {
       FirebaseStorage.getInstance()
   }

    fun getMyStorageRef(): FirebaseStorage {
        return storageInstance
    }





}
