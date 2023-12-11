package com.example.chat.data.dataBase.model

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.Timestamp

data class Message(
    val messagesId :String? =null,
    val content :String? = null,
    val roomId :String? = null,
    val senderName : String ? = null,
    val senderId :String ? = null,
    var time :Timestamp = Timestamp.now()
)
{
    companion object{
        fun convertTimeStampToDate(dateTime: Timestamp):String{
            val simpleDateFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
            return simpleDateFormat.format(dateTime.toDate())
        }

    }
}

