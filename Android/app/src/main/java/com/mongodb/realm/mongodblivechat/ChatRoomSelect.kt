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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatRoomSelect.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatRoomSelect : Fragment() {

    lateinit var binding:FragmentChatRoomSelectBinding

    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.email = it.getString("email")

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        binding.roomModel?.chatUser = this.email.toString()

        return binding.root
    }

    interface OnFragmentInteractionListener {
        //TODO: Update argument type and name
        fun OnFragmentInteractionListener(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatRoomSelect.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ChatRoomSelect().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}