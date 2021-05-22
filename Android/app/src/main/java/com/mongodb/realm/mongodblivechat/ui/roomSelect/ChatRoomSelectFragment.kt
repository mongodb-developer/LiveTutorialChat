package com.mongodb.realm.mongodblivechat.ui.roomSelect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import com.mongodb.realm.mongodblivechat.ui.roomSelect.ChatRoomSelectFragmentDirections.ActionChatRoomSelectToChatFragment
import kotlinx.android.synthetic.main.chat_room_select_fragment.*
import com.mongodb.realm.mongodblivechat.databinding.ChatRoomSelectFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class ChatRoomSelectFragment : Fragment() {

    lateinit var binding:ChatRoomSelectFragmentBinding

    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Capture email argument passed by navigation action from LoginFragment
        arguments?.let {
            this.email = it.getString("email")

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val action : ActionChatRoomSelectToChatFragment =
            com.mongodb.realm.mongodblivechat.ui.roomSelect.ChatRoomSelectFragmentDirections.actionChatRoomSelectToChatFragment()
        action.setEmail(email)
        action.setChatRoom(room)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val model : RoomViewModel by viewModels()
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat_room_select, container, false)


        binding = ChatRoomSelectFragmentBinding.inflate(inflater, container, false).apply {
            roomModel = model
            lifecycleOwner = viewLifecycleOwner
        }

        binding.roomModel?.chatUser = this.email.toString()

        return binding.root
    }

}