package com.example.chat.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.data.dataBase.model.RoomCategory
import com.example.chat.databinding.RoomCategoryItemBinding

class RoomCategoryAdapter(private val itemsList : List<RoomCategory>) : BaseAdapter() {
    override fun getCount(): Int {
        return itemsList.size
    }

    override fun getItem(position: Int): Any {
       return itemsList[position]
    }

    override fun getItemId(position: Int): Long {
        return itemsList[position].id!!.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewHolder : ViewHolder

        if (view == null){
            val viewBinding = RoomCategoryItemBinding.inflate(LayoutInflater.from(parent?.context)
                ,parent,false)

                 viewHolder = ViewHolder(viewBinding)
                viewBinding.root.tag = viewHolder
        }else{
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.bind(itemsList[position])

        return viewHolder.viewBinding.root
    }

    class ViewHolder(val viewBinding : RoomCategoryItemBinding) {
        fun bind(roomCategory: RoomCategory) {
            viewBinding.image.setImageResource(roomCategory.image!!)
            viewBinding.categoryName.text = roomCategory.categoryName
        }
    }
}