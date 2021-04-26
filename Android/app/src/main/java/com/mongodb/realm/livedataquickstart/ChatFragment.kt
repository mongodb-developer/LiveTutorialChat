package com.mongodb.realm.livedataquickstart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mongodb.realm.livedataquickstart.databinding.ChatFragmentBinding
import com.mongodb.realm.livedataquickstart.model.ChatModel
import kotlinx.android.synthetic.main.chat_fragment.view.*
import android.net.Uri
import androidx.lifecycle.Observer
import com.mongodb.realm.livedataquickstart.model.ChatMessage


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatFragment : Fragment() {

    lateinit var binding:ChatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val model: ChatModel by viewModels()

        binding = ChatFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            myChatModel = model
        }

        val messageObserver = Observer<List<ChatMessage>?> {
                cMessages -> model.setMessageHistoryText(cMessages)
        }

        model._chatMessages.observe(this.viewLifecycleOwner, messageObserver)

        this.activity

        /*
        // Replaced by onClick listener in counter_fragment.xml
        binding.root.button.setOnClickListener {
            Log.v("QUICKSTART", "Clicked increment button. Current value: ${model.counter.value?.value?.get()}")
            model.incrementCounter()
        }
        */

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        arguments?.let {
            val args = ChatFragmentArgs.fromBundle(it)
 //           this.binding.getCounterModel()!!.enteredEmail = args.email
 //           this.binding.getCounterModel()!!.enteredPassword = args.password

            this.binding.getMyChatModel()!!.connToRealmApp(args.email)

        }
    }

    //TODO: Is this necessary?????
    interface OnFragmentInteractionListener {
        //TODO: Update argument type and name
        fun OnFragmentInteractionListener(uri: Uri)
    }
}