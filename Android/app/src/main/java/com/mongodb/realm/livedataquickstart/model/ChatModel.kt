package com.mongodb.realm.livedataquickstart.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import androidx.lifecycle.MutableLiveData
import com.mongodb.realm.livedataquickstart.chatApp
import io.realm.RealmResults

class ChatModel() : ViewModel() {

    private var realm: Realm? = null
    private var chatUser = ""

    var _chatMessages : LiveRealmResults<ChatMessage>

    var messageText = "" //maps to send message text box in UI
    var messageHistory : MutableLiveData<String> = MutableLiveData()



    fun sendMessage() {
        val msg = ChatMessage(rm = "123", user = this.chatUser, msg = this.messageText)
        this.realm?.executeTransaction{ transactionRealm ->
            transactionRealm.insert(msg)
            Log.v("QUICKSTART", "\"$messageText\" inserted.")
        }
    }


    fun setMessageHistoryText(msgs : List<ChatMessage> ) {
        //return "first message \n, second message \n"
        var messageStr = ""

        //val msgs : RealmResults<ChatMessage> = this.realm?.where<ChatMessage>()!!.findAll()


        for (msg in msgs) {
            messageStr = messageStr + "[" + msg.getAuthorName() + "] " + msg.text + "\n"
        }
        messageHistory.value = messageStr
    }

        override fun onCleared() {
        this.realm?.close()
    }


    init {
        val user: User? =  chatApp.currentUser()
        val partitionValue = "example partition"
        val config = SyncConfiguration.Builder(user!!, partitionValue)
            // because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        // open the realm
        this.realm = Realm.getInstance(config)

        _chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll())
        setMessageHistoryText(_chatMessages.value!!)
    }



    //TODO: Add realm close on CounterFragment.onDestroyView
    fun connToRealmApp (enteredEmail: String) {

        this.chatUser = enteredEmail
    }
}
