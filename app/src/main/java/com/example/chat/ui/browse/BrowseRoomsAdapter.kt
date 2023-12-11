package com.example.chat.ui.browse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.data.dataBase.model.Room
import com.example.chat.data.dataBase.model.RoomCategory
import com.example.chat.databinding.RoomItemBinding

class BrowseRoomsAdapter(private var itemsList : List<Room>?)  : Adapter<BrowseRoomsAdapter.BrowseRoomsViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseRoomsViewHolder {
        val viewBinding = RoomItemBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return BrowseRoomsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return itemsList?.size?:0
    }

    override fun onBindViewHolder(holder: BrowseRoomsViewHolder, position: Int) {
        val item = itemsList!![position]
        holder.bind(item)
        holder.viewBinding.root.setOnClickListener{
            onRoomClickListener.onClick(item)
        }
    }


    fun bindData(rooms: List<Room>?) {
        itemsList = rooms
        notifyDataSetChanged()
    }

    lateinit var onRoomClickListener: OnRoomClickListener
    fun interface OnRoomClickListener{
        fun onClick(room:Room)
    }
    class BrowseRoomsViewHolder( val viewBinding:RoomItemBinding) : ViewHolder(viewBinding.root){
            fun bind(item: Room) {
                viewBinding.image.setImageResource(
                    RoomCategory.getRoomImageCategoryById(item.categoryId!!)!!
                )
                viewBinding.title.text = item.roomName
                viewBinding.members.text = "${item.membersId?.size} members"

        }

    }
}