package com.example.chat.data.dataBase.fireStore

import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.Arrays

object RoomsDao {
     fun getRoomCollectionReference(): CollectionReference {
        val fireStore = FirebaseFirestore.getInstance()
        return fireStore.collection("rooms")
    }

    fun insertRoomInFireStore(
        roomName:String,
        roomDesc:String,
        categoryId : Int,
        memberId:String,
        onCompleteListener:OnCompleteListener<Void>
    ){
        val document = getRoomCollectionReference().document()


        val roomId = document.id
        val members = mutableListOf<String>()
        members.add(memberId)
        val room = Room(
            roomName= roomName, categoryId = categoryId,
            roomDescription = roomDesc, ownerId = SessionProvider.currentUser?.uid
            , membersId = members , roomId = roomId
        )

        document.set(room)
            .addOnCompleteListener(onCompleteListener)
    }

    fun getAllRoomsFromCollection(onCompleteListener: OnCompleteListener<QuerySnapshot>){
        getRoomCollectionReference()
            .get().
            addOnCompleteListener(onCompleteListener)
    }

    fun getJoinedRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>) {
        val uid = SessionProvider.currentUser?.uid
        getRoomCollectionReference()
            .whereArrayContains("membersId", uid.toString())
            .get()
            .addOnCompleteListener(onCompleteListener)

    }


    fun update(roomId :String,newUserId:String,onCompleteListener: OnCompleteListener<Void>){
        getRoomCollectionReference()
            .document(roomId)
            .update("membersId",FieldValue.arrayUnion(newUserId))
            .addOnCompleteListener(onCompleteListener)
    }



}