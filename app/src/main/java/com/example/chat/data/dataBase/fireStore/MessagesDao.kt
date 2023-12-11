package com.example.chat.data.dataBase.fireStore

import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.model.Message
import com.example.chat.data.dataBase.model.Room
import com.google.firebase.firestore.CollectionReference

object MessagesDao {
     fun getMessagesCollectionRef(room: Room): CollectionReference {

        return RoomsDao.getRoomCollectionReference()
            .document(room.roomId!!)
            .collection("messages")


    }
}