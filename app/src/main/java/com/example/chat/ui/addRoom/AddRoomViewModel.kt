package com.example.chat.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.RoomsDao
import com.example.chat.data.dataBase.model.RoomCategory
import com.example.chat.ui.Message

class AddRoomViewModel : ViewModel() {

    val message = SingleLiveEvent<Message>()
    val categories = RoomCategory.getCategories()
    val roomNameLiveData = MutableLiveData<String>()
    val roomDescriptionLiveData = MutableLiveData<String>()
    val roomNameError = MutableLiveData<String>()
    val roomDescriptionError = MutableLiveData<String>()
    val navigationEvent = SingleLiveEvent<AddRoomNavigation>()
    var selectedCategory = categories[0]


    fun createRoom(){
        if (!validForm()){
            return
        }
        RoomsDao.insertRoomInFireStore(
            roomName = roomNameLiveData.value!!, roomDesc = roomDescriptionLiveData.value!!,
            categoryId = selectedCategory.id!!, memberId = SessionProvider.currentUser?.uid?:""
        ){task->

            if (task.isSuccessful){
                message.value = Message(message = "Room Created Successfully"
                    , posMessage = "Ok", posAction = {
                        navigateToHome()
                    }
                )

            }else{
                message.value = Message(message = task.exception?.localizedMessage)
            }
        }

    }

    fun navigateToHome(){
        navigationEvent.value = AddRoomNavigation.NavigateToHome
    }

    fun setCategorySelected(position: Int) {
       selectedCategory = categories[position]
    }




    private fun validForm() : Boolean {
        var isValid = true
        if (roomNameLiveData.value.isNullOrBlank()&& roomDescriptionLiveData.value.isNullOrBlank()){
            roomNameError.value = "Please Enter Room Name"
            roomDescriptionError.value = "Please Enter Room Description"
            isValid = false
        }else if (roomNameLiveData.value.isNullOrBlank()){
            roomNameError.value = "Please Enter Room Name"
            isValid = false
        }else if (roomDescriptionLiveData.value.isNullOrBlank()){
            roomDescriptionError.value = "Please Enter Room Description"
            isValid = false
        }
        return isValid
    }
}