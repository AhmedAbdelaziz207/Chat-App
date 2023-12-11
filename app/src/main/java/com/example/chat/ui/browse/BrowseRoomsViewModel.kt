package com.example.chat.ui.browse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.fireStore.RoomsDao
import com.example.chat.data.dataBase.model.Room
import com.example.chat.ui.Message

class BrowseRoomsViewModel:ViewModel() {
    val allRoomsLiveData = MutableLiveData<List<Room>>()
    val messageLiveData  = SingleLiveEvent<Message>()
    val joinedRooms = MutableLiveData<List<Room>>()


    fun getJoinedRooms(){
        RoomsDao.getJoinedRooms{task->
            joinedRooms.value = task.result.toObjects(Room::class.java)
        }
    }


    fun isJoinedRoom(roomId:String):Boolean{
        val rooms = joinedRooms.value
       rooms?.forEach{room: Room ->
           if (room.roomId == roomId){
              return true
           }
       }
        return false
    }


    fun getAllRooms(){
        RoomsDao.getAllRoomsFromCollection { task->
            if (task.isSuccessful){
                val rooms =  task.result.toObjects(Room::class.java)
                allRoomsLiveData.postValue(rooms)
            }else{
                messageLiveData.postValue(Message(message = task.exception?.localizedMessage))
            }

        }
    }
}