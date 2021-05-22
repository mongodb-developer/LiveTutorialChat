package com.mongodb.realm.mongodblivechat.model

import android.util.Log
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import androidx.lifecycle.MutableLiveData
import io.realm.mongodb.App

class ChatModel(private val chatApp: App) : ViewModel() {

    private var realm: Realm? = null
    var chatUser = ""
    var chatRoom = ""

    var _chatMessages: LiveRealmResults<ChatMessage>? = null

    var messageText = "" //maps to send message text box in UI
    var messageHistory: MutableLiveData<String> = MutableLiveData()


    fun sendMessage() {
        val msg = ChatMessage(rm = this.chatRoom, user = this.chatUser, msg = this.messageText)
        this.realm?.executeTransaction { transactionRealm ->
            transactionRealm.insert(msg)
            Log.v(
                "QUICKSTART",
                "\"$messageText\" inserted in Room: ${this.chatRoom} for user: ${this.chatUser}"
            )
        }
        this.messageText = ""
    }


    fun setMessageHistoryText(msgs: List<ChatMessage>) {
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


    private fun openRealm() {
        val user: User? = chatApp.currentUser()
        val partitionValue = this.chatRoom
        //var partitionValue = "example partition"

        Log.v("QUICKSTART", "Opening Realm. The chat room is: " + this.chatRoom)
        //TODO: [Step 3a] Configure realm


        //TODO: [Step 3b] Open the realm


        //TODO: [Step 4a] Populate _chatMessages with the ChatMessages in the realm (chat room)


        //TODO: [Step 4b] Update chat history text box based upon ChatMessages

    }

    fun closeRealm() {
        this.realm?.close()
    }

    fun connToRealmApp(enteredEmail: String, selectedChatRoom: String) {

        this.chatUser = enteredEmail
        this.chatRoom = selectedChatRoom

        openRealm()
    }
}
