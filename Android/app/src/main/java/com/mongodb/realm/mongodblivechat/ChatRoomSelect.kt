package com.mongodb.realm.mongodblivechat

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import com.mongodb.realm.mongodblivechat.ChatRoomSelectDirections.ActionChatRoomSelectToChatFragment
import kotlinx.android.synthetic.main.fragment_chat_room_select.*
import com.mongodb.realm.mongodblivechat.databinding.FragmentChatRoomSelectBinding
import com.mongodb.realm.mongodblivechat.model.RoomModel

/**
 * A simple [Fragment] subclass.
 */
class ChatRoomSelect : Fragment() {

    lateinit var binding:FragmentChatRoomSelectBinding

    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Capture email argument passed by navigation action from LoginFragment
        arguments?.let {
            this.email = it.getString("email")

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO: [Step 2b] - Save selected chatroom to the RoomModel and navigate to the ChatFragment while
        //   passing the user and chat room selection (see fun openChatWindow)
        serverButton.setOnClickListener{
            binding.roomModel?.chatRoom = "Atlas"
            openChatWindow(it, binding.roomModel?.chatUser.toString(), binding.roomModel?.chatRoom.toString())
        }

        searchButton.setOnClickListener{
            binding.roomModel?.chatRoom = "Search"
            openChatWindow(it, binding.roomModel?.chatUser.toString(), binding.roomModel?.chatRoom.toString())
        }

        chartsButton.setOnClickListener{
            binding.roomModel?.chatRoom = "Charts"
            openChatWindow(it, binding.roomModel?.chatUser.toString(), binding.roomModel?.chatRoom.toString())
        }

        realmButton.setOnClickListener{
            binding.roomModel?.chatRoom = "Realm"
            openChatWindow(it, binding.roomModel?.chatUser.toString(), binding.roomModel?.chatRoom.toString())
        }

        atlasButton.setOnClickListener{
            binding.roomModel?.chatRoom = "Atlas"
            openChatWindow(it, binding.roomModel?.chatUser.toString(), binding.roomModel?.chatRoom.toString())
        }

    }

    fun openChatWindow(view: View, email: String, room: String) {
        //TODO: [Step 2c] - Create an ActionChatRoomSelectToChatFragment action (note, these classes and methods are automatically generated
        //   by the androidx Navigation libraries
        //   - Set the email and chatRoom parameters of the action
        //   - Navigate to the ChatFragment passing the action
        val action : ActionChatRoomSelectToChatFragment = ChatRoomSelectDirections.actionChatRoomSelectToChatFragment()

        action.setEmail(email)
        action.setChatRoom(room)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val model : RoomModel by viewModels()
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat_room_select, container, false)


        binding = FragmentChatRoomSelectBinding.inflate(inflater, container, false).apply {
            roomModel = model
            lifecycleOwner = viewLifecycleOwner
        }

        //TODO: [STEP 2a] - save email passed by navigation from the LoginFragment to the RoomModel ViewModel
        binding.roomModel?.chatUser = this.email.toString()

        return binding.root
    }

}