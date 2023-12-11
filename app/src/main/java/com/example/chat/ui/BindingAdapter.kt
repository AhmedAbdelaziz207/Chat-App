package com.example.chat.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.chat.data.dataBase.model.RoomCategory

//@BindingAdapter("Error")
//fun bindErrorInInputField(textView: TextView,message:String){
//    textView.error = message
//}

@BindingAdapter("loading")
fun showProgressBar(progressBar: ProgressBar,isVisible:Boolean){
    if (isVisible){
        progressBar.visibility = View.VISIBLE
    }else{
        progressBar.visibility = View.GONE
    }

}
@BindingAdapter("viewVisibility")
fun showView(imageView: View, isVisible:Boolean){
    if (isVisible){
        imageView.visibility = View.VISIBLE
    }else{
        imageView.visibility = View.GONE
    }
}
@BindingAdapter("Text")
fun setText(textview:TextView,text:String){
    textview.text = "Join, $text"
}
@BindingAdapter("Src")
fun setImageResource(imageView: ImageView, uid:Int){
    val imageRes = RoomCategory.getRoomImageCategoryById(uid)
    imageView.setImageResource(imageRes!!)
}