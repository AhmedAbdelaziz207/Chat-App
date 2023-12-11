package com.example.chat.data.dataBase.model

import com.example.chat.R

data class RoomCategory(
    val id : Int ? = null,
    val image : Int ? = null,
    val categoryName : String? = null
){
    companion object{


    fun getCategories():List<RoomCategory>{
        return listOf(
            RoomCategory(id= 1 , categoryName = "Sports", image = R.drawable.sports),
            RoomCategory(id= 2 , categoryName = "Music", image = R.drawable.music),
            RoomCategory(id= 3 , categoryName = "Movies", image = R.drawable.movies),
        )
    }

        fun getRoomImageCategoryById(id:Int):Int?{
            var image : Int? = null
            when(id){
                1->image = R.drawable.sports
                2->image = R.drawable.music
                3->image = R.drawable.movies
            }
            return image
        }
    }
}
