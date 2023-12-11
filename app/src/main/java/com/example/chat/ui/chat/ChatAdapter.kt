package com.example.chat.ui.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.model.Message
import com.example.chat.databinding.MessageItemRecievedBinding
import com.example.chat.databinding.MessageItemSendBinding

class ChatAdapter(private var itemList:MutableList<Message> ?= null ):Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (SessionProvider.currentUser?.uid == itemList!![position].senderId)
            ViewType.Sender.ordinal
        else
            ViewType.Receiver.ordinal
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ViewType.Sender.ordinal){
            val viewBinding = MessageItemSendBinding.inflate(LayoutInflater.from(
                parent.context),parent,false)
            SendMessageViewHolder(viewBinding)
        }else{
            val viewBinding = MessageItemRecievedBinding.inflate(LayoutInflater.from(
                parent.context),parent,false)
            ReceivedMessageViewHolder(viewBinding)
        }

    }

    override fun getItemCount(): Int {
        return itemList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList!![position]
        when(holder){
            is SendMessageViewHolder->{
                holder.bind(item)
            }
            is ReceivedMessageViewHolder->{
                holder.bind(item)

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindData(messages: List<Message>) {
        itemList = messages.toMutableList()
        notifyDataSetChanged()
    }

    fun addNewMessages(newMessages: List<Message>?) {
        if (newMessages == null) return
        val oldSize = itemList?.size?:0
        itemList?.addAll(newMessages)
        notifyItemRangeInserted(oldSize, newMessages.size)
    }

    class SendMessageViewHolder(val viewBinding :MessageItemSendBinding):ViewHolder(viewBinding.root) {
        fun bind(item:Message) {
            viewBinding.messageTxt.text = item.content
            viewBinding.time.text = Message.convertTimeStampToDate(item.time)
        }
    }

    class ReceivedMessageViewHolder(val viewBinding :MessageItemRecievedBinding):ViewHolder(viewBinding.root) {
        fun bind(item: Message) {
            viewBinding.messageTxt.text = item.content
           viewBinding.time.text = Message.convertTimeStampToDate(item.time)
            viewBinding.name.text= item.senderName
        }
    }

    enum class ViewType{
        Sender,
        Receiver
    }
}