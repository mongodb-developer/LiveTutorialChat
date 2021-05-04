package com.mongodb.realm.mongodblivechat.model

import android.util.Log
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import androidx.lifecycle.MutableLiveData
import com.mongodb.realm.mongodblivechat.chatApp

class ChatModel() : ViewModel() {

    private var realm: Realm? = null
    var chatUser = ""
    var chatRoom = ""

    var _chatMessages : LiveRealmResults<ChatMessage>? = null

    var messageText = "" //maps to send message text box in UI
    var messageHistory : MutableLiveData<String> = MutableLiveData()



    fun sendMessage() {
        val msg = ChatMessage(rm = "123", user = this.chatUser, msg = this.messageText)
        this.realm?.executeTransaction{ transactionRealm ->
            transactionRealm.insert(msg)
            Log.v("QUICKSTART", "\"$messageText\" inserted.")
         }
        this.messageText = ""
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

/*
    init {
        openRealm()
    }

 */

    fun openRealm() {
        val user: User? =  chatApp.currentUser()
        val partitionValue = this.chatRoom
        //var partitionValue = "example partition"

        Log.v("QUICKSTART", "Openning Realm. The chat room is: " + this.chatRoom)
        val config = SyncConfiguration.Builder(user!!, partitionValue)
            // because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        // open the realm
        this.realm = Realm.getInstance(config)

        _chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll().sort("timestamp"))

        //TODO: doesn't work when room is empty
        setMessageHistoryText(_chatMessages!!.value!!)
    }






    //TODO: Add realm close on CounterFragment.onDestroyView
    fun connToRealmApp (enteredEmail: String, selectedChatRoom: String) {

        this.chatUser = enteredEmail
        this.chatRoom = selectedChatRoom

        openRealm()
    }
}
