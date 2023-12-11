package com.example.chat.ui.myRooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.fireStore.RoomsDao
import com.example.chat.data.dataBase.model.Room
import com.example.chat.ui.Message

class MyRoomsViewModel:ViewModel() {

    val myRoomsLiveData = MutableLiveData<List<Room>>()
    val message = SingleLiveEvent<Message>()
    val noRoomsMessage = MutableLiveData(true)


    fun getJoinedRoomFromFireStore(){

        RoomsDao.getJoinedRooms { task->
            if (task.isSuccessful){
                if (task.result != null){
                    noRoomsMessage.value = false
                    myRoomsLiveData.value = task.result.toObjects(Room::class.java)
                }
            }else{
                message.value = Message(message = task.exception?.localizedMessage)
            }
        }
    }

}