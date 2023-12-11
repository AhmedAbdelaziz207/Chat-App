package com.example.chat.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.MessagesDao
import com.example.chat.data.dataBase.model.Message
import com.example.chat.data.dataBase.model.Room
import com.google.firebase.firestore.DocumentChange

class ChatViewModel:ViewModel() {
    var room: Room? = null
    val messageContentLive = MutableLiveData<String>()
    val toast = SingleLiveEvent<Boolean>()
    val chatEvent = SingleLiveEvent<Boolean>()
    val newMessages = SingleLiveEvent<List<Message>>()
    val messagesLiveData = MutableLiveData<List<Message>>()
    fun sendMessage() {
        if (messageContentLive.value.isNullOrBlank()) return
        insertMessageInFireStore()
    }

    fun getAllMessages() {
       MessagesDao
            .getMessagesCollectionRef(room!!)
            .orderBy("time")
            .get().addOnCompleteListener{
                if (it.isSuccessful){
                    messagesLiveData.value = it.result.toObjects(Message::class.java)
                }
           }
    }

    fun listenForNewMessages() {

        MessagesDao
            .getMessagesCollectionRef(room!!)
            .orderBy("time")
            .limit(100)
            .addSnapshotListener{snapshots,error->
                val newMessages = mutableListOf<Message>()
                for (doc in snapshots!!.documentChanges) {
                    when (doc.type) {
                        DocumentChange.Type.ADDED -> newMessages.add(doc.document.toObject(Message::class.java))
                        else -> {}
                    }
                }
                this.newMessages.value = newMessages

            }

    }

    fun navigateToHome(){
        chatEvent.value = true
    }
    private fun insertMessageInFireStore(){

        val messageDoc =  MessagesDao.getMessagesCollectionRef(room!!)
            .document()

        val message = Message(
            content = messageContentLive.value,roomId = room?.roomId,
            senderName = SessionProvider.currentUser?.userName, senderId = SessionProvider.currentUser?.uid,
            messagesId = messageDoc.id
        )
        messageDoc.set(message)
            .addOnCompleteListener{
                if (it.isSuccessful)
                    messageContentLive.value = ""
                else{
                    toast.value = true
                }
            }

    }
}