package com.example.chat.data.dataBase.model

import java.io.Serializable

data class Room(
    val roomName : String? = null,
    val categoryId : Int ? = null,
    val roomDescription : String? = null,
    val ownerId: String? = null,
    val roomId:String ? =null,
    val membersId:MutableList<String>?= mutableListOf()
):Serializable
