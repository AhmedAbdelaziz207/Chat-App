package com.example.chat.ui

data class Message(
     val message : String? = null,
     val posMessage : String? = null
    ,val posAction: OnDialogActionClickListener? = null
    ,val negMessage : String? = null
    ,val negAction: OnDialogActionClickListener? = null
    ,val isCancellable : Boolean = true

)
fun interface OnDialogActionClickListener{
    fun onDialogAction()
}