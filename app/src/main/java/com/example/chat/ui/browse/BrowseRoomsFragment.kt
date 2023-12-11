package com.example.chat.ui.browse

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Constants
import com.example.chat.data.dataBase.model.Room
import com.example.chat.databinding.FragmentBrowseBinding
import com.example.chat.ui.chat.ChatActivity
import com.example.chat.ui.join.JoinRoomsActivity
import com.example.chat.ui.showDialog


class BrowseRoomsFragment : Fragment() {
    private lateinit var viewBinding: FragmentBrowseBinding
    lateinit var viewModel: BrowseRoomsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            viewBinding = FragmentBrowseBinding.inflate(layoutInflater)
            return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRooms()
        viewModel.getJoinedRooms()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BrowseRoomsViewModel::class.java]
        initViews()
    }
    private lateinit var adapter:BrowseRoomsAdapter


    private fun initViews() {
        adapter = BrowseRoomsAdapter(null)
        viewBinding.recyclerView.adapter = adapter

        adapter.onRoomClickListener = BrowseRoomsAdapter.OnRoomClickListener { room->
            if (viewModel.isJoinedRoom(room.roomId!!))
                navigateToChatActivity(room)
            else
                navigateToJoinActivity(room)
        }


        observeOnLiveData()
    }

    private fun navigateToChatActivity(room: Room) {
        val intent  = Intent(context,ChatActivity::class.java)
        intent.putExtra(Constants.Room,room)
        startActivity(intent)
    }

    private fun navigateToJoinActivity(room: Room) {
        val intent  = Intent(context,JoinRoomsActivity::class.java)
        intent.putExtra(Constants.Room,room)
        startActivity(intent)

    }

    private fun observeOnLiveData() {
        viewModel.allRoomsLiveData.observe(viewLifecycleOwner){ rooms->
            adapter.bindData(rooms)
        }
        viewModel.messageLiveData.observe(viewLifecycleOwner){
            showDialog(message = it.message.toString())
        }


    }

}