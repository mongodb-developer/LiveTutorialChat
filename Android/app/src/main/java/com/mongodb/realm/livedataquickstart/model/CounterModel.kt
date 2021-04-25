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

class CounterModel() : ViewModel() {

    private var realm: Realm? = null
    private var chatUser = ""
    private val _counter: LiveRealmObject<Counter> = LiveRealmObject(null)
    val counter: LiveData<Counter>
        get() = this._counter

    lateinit var _chatMessages : LiveRealmResults<ChatMessage>

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

    fun incrementCounter() {
        this.realm?.executeTransaction {
            this.counter.value?.let { counterValue ->
                counterValue.add()
                this._counter.postValue(counterValue)
                Log.v("QUICKSTART", "Incremented counter. New value: ${counterValue.value.get()}")
            }
        }
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
    fun connToRealmApp (enteredEmail: String, enteredPassword : String) {

        this.chatUser = enteredEmail

        // 3. connect to a realm with Realm Sync


        // 4. Query the realm for a Counter, creating a new Counter if one doesn't already exist
        // access all counters stored in this realm
        val counterQuery = this.realm!!.where<Counter>()
        val counters = counterQuery.findAll()

        // if we haven't created the one counter for this app before (as on first launch), create it now
        if (counters.size == 0) {
            this.realm?.executeTransaction { transactionRealm ->
                val counter = Counter()
                transactionRealm.insert(counter)
            }
        }

        // 5. Instantiate a LiveRealmObject using the Counter and store it in a member variable
        // the counters query is life, so we can just grab the 0th index to get a guaranteed counter
        this._counter.postValue(counters[0]!!)
        //_chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll())

        //this.messageHistory.value = getMessages()
    }
}
