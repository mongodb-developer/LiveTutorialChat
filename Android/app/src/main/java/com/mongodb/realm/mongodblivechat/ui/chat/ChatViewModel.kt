package com.mongodb.realm.mongodblivechat.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import androidx.lifecycle.MutableLiveData
import com.mongodb.realm.mongodblivechat.realmsync.ChatMessage
import com.mongodb.realm.mongodblivechat.realmsync.LiveRealmResults
import io.realm.mongodb.App

class ChatViewModel(private val chatApp: App) : ViewModel() {

    private var realm: Realm? = null
    var chatUser = ""
    var chatRoom = ""

    var _chatMessages: LiveRealmResults<ChatMessage>? = null

    var messageText = "" //maps to send message text box in UI
    var messageHistory: MutableLiveData<String> = MutableLiveData()


    fun sendMessage() {
        val msg = ChatMessage(rm = this.chatRoom, user = this.chatUser, msg = this.messageText)

        //TODO [Step 4c] - Insert new chat message into Realm
        this.realm?.executeTransaction { transactionRealm ->
            transactionRealm.insert(msg)
            Log.v(
                "QUICKSTART",
                "\"$messageText\" inserted in Room: ${this.chatRoom} for user: ${this.chatUser}"
            )
        }
        this.messageText = ""
    }


    fun setMessageHistoryText(msgs: List<ChatMessage>?) {
        //return "first message \n, second message \n"
        var messageStr = ""

        //val msgs : RealmResults<ChatMessage> = this.realm?.where<ChatMessage>()!!.findAll()
        msgs?.let {
            for (msg in it) {
                messageStr = messageStr + "[" + msg.getAuthorName() + "] " + msg.text + "\n"
            }
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
        val config = SyncConfiguration.Builder(user!!, partitionValue)
            // because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        //TODO: [Step 3b] Open the realm
        this.realm = Realm.getInstance(config)

        //TODO: [Step 4a] Populate _chatMessages with the ChatMessages in the realm (chat room)
        _chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll().sort("timestamp"))

        //TODO: [Step 4b] Update chat history text box based upon ChatMessages
        _chatMessages?.let{
            setMessageHistoryText(it.value)
        }
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
