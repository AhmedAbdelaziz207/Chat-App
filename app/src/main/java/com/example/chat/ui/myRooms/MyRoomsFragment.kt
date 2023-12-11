package com.example.chat.ui.myRooms

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Constants
import com.example.chat.data.dataBase.model.Room
import com.example.chat.databinding.FragmentMyRoomsBinding
import com.example.chat.ui.chat.ChatActivity


class MyRoomsFragment : Fragment() {
    lateinit var viewBinding: FragmentMyRoomsBinding
    lateinit var viewModel: MyRoomsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMyRoomsBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MyRoomsViewModel::class.java]
        initViews()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getJoinedRoomFromFireStore()
    }
    private lateinit var adapter: MyRoomsAdapter
    private fun initViews() {
        adapter = MyRoomsAdapter(null)
        viewBinding.recyclerView.adapter = adapter
        adapter.onChatClickListener = MyRoomsAdapter.OnChatClickListener { room->
            navigateToChatActivity(room)
        }

        observeOnLiveData()
    }

    private fun navigateToChatActivity(room: Room) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(Constants.Room,room)
        startActivity(intent)
    }

    private fun observeOnLiveData() {
        viewModel.myRoomsLiveData.observe(viewLifecycleOwner){rooms->
            adapter.bindData(rooms)
        }
    }
}