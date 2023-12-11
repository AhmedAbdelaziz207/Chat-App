package com.example.chat.ui.addRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.ViewModelProvider

import com.example.chat.databinding.ActivityAddRoomBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.showDialog

class AddRoomActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivityAddRoomBinding
    private lateinit var viewModel: AddRoomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[AddRoomViewModel::class.java]
        initViews()
    }
    private lateinit var adapter : RoomCategoryAdapter

    private fun initViews() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this
        observeOnLiveData()
        adapter = RoomCategoryAdapter(viewModel.categories)
        viewBinding.content.categorySpinner.adapter = adapter


        viewBinding.content.categorySpinner.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

           }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setCategorySelected(position)

                Log.d("TAG", viewModel.selectedCategory.toString())

            }
        }
    }

    private fun observeOnLiveData() {
        viewModel.message.observe(this){message->
            showDialog(message = message.message.toString()
            , isCancellable = false, posAction = message.posAction, posMessage = message.posMessage
            , negAction = message.negAction, negMessage = message.negMessage)
        }
        viewModel.navigationEvent.observe(this){
            when(it){
               AddRoomNavigation.NavigateToHome->{
                   navigateToHome()
               }

            }

        }
    }

    private fun navigateToHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun backFromCurrentActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}