package com.mongodb.realm.mongodblivechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mongodb.realm.mongodblivechat.databinding.ChatFragmentBinding
import com.mongodb.realm.mongodblivechat.model.ChatModel
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mongodb.realm.mongodblivechat.model.ChatMessage


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatFragment : Fragment() {

    private lateinit var binding:ChatFragmentBinding

    val model: ChatModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val realmApp = (requireActivity().application as LiveChatApplication).chatApp
                return ChatModel(realmApp) as T
            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {


        binding = ChatFragmentBinding.inflate(inflater, container, false).apply {
            myChatModel = model
            lifecycleOwner = viewLifecycleOwner
        }
        //binding.root.sendMessageButton.setOnClickListener(){
        binding.sendMessageButton.setOnClickListener{
            Log.v("QUICKSTART", "Running on click listener")
            model.sendMessage()
            this.sendMessage()
        }

        binding.switchRoomButton.setOnClickListener{
            val action : ChatFragmentDirections.ActionBackToChatFragmentToChatRoomSelect= ChatFragmentDirections.actionBackToChatFragmentToChatRoomSelect()
            action.email = binding.myChatModel!!.chatUser
            Navigation.findNavController(it).navigate(action)
            model.closeRealm()
        }

         return binding.root
    }
/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
*/
    private fun sendMessage() {
        Log.v("QUICKSTART", "Clearing text box")
        binding.enterMessageBox.text.clear()
    }

    override fun onStart() {
        super.onStart()
        
        arguments?.let {
            val args = ChatFragmentArgs.fromBundle(it)

            this.binding.myChatModel!!.connToRealmApp(args.email, args.chatRoom)

            val messageObserver = Observer<List<ChatMessage>?> {
                    cMessages ->
                binding.myChatModel?.setMessageHistoryText(cMessages)
                Log.v("QUICKSTART", "Updating message history list")
            }

            binding.myChatModel?._chatMessages?.observe(viewLifecycleOwner, messageObserver)

        }
    }
}