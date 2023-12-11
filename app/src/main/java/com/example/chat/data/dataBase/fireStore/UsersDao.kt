package com.example.chat.data.dataBase.fireStore

import com.example.chat.data.dataBase.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


object UsersDao {
     private fun getFireStoreCollection():CollectionReference{
        val fireStore = FirebaseFirestore.getInstance()
        return fireStore.collection("Users")
    }
    fun saveAccountInFireStore(userAcc: User, onCompleteListener:OnCompleteListener<Void>){
        getFireStoreCollection().document(userAcc.uid?:"")
            .set(userAcc)

            .addOnCompleteListener(onCompleteListener)

    }

    fun getUserFromFireStore(uid:String,onCompleteListener: OnCompleteListener<DocumentSnapshot>): Task<DocumentSnapshot> {
        return getFireStoreCollection().document(uid)
            .get().addOnCompleteListener(onCompleteListener)
    }

    fun isSignedIn():Boolean{
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }
}