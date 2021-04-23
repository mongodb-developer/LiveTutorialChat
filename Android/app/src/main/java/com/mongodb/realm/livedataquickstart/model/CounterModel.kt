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

class CounterModel() : ViewModel() {
    private val appID = "quickstart2-xjrwi"
    lateinit var app : App
    private var realm: Realm? = null
    private var chatUser = ""
    private val _counter: LiveRealmObject<Counter> = LiveRealmObject(null)
    val counter: LiveData<Counter>
        get() = this._counter

    var messageText = ""
    var messageHistory : MutableLiveData<String> = MutableLiveData()

    fun sendMessage() {
        val msg : ChatMessage = ChatMessage(rm = "123", user = this.chatUser, msg = this.messageText)
        this.realm?.executeTransaction{ transactionRealm ->
            transactionRealm.insert(msg)
            Log.v("QUICKSTART", "\"$messageText\" inserted.")
        }
    }

    fun getMessages(): String {
        return "first message \n, second message \n"
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

    /*
    init {
        connToRealmApp()
    }
     */


    //TODO: Move this to onCreateView method on CounterFragment (or maybe on the activity)
    //TODO: Add realm close on CounterFragment.onDestroyView
    fun connToRealmApp (enteredEmail: String, enteredPassword : String) {


        // 1. connect to the MongoDB Realm app backend
        this.app = App(
            AppConfiguration.Builder(this.appID)
                .build()
        )



        // 2. authenticate a user
        this.app.loginAsync(Credentials.emailPassword(enteredEmail, enteredPassword)) {
            if(it.isSuccess) {
                Log.v("QUICKSTART", "Successfully logged in Email: $enteredEmail, Password: $enteredPassword")
                this.chatUser = enteredEmail
                // 3. connect to a realm with Realm Sync
                val user: User? = this.app.currentUser()
                val partitionValue = "example partition"
                val config = SyncConfiguration.Builder(user!!, partitionValue)
                    // because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread
                    .allowWritesOnUiThread(true)
                    .allowQueriesOnUiThread(true)
                    .build()

                // open the realm
                this.realm = Realm.getInstance(config)

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
                this.messageHistory.value = getMessages()

            } else {
                Log.e("QUICKSTART", "Failed to log in anonymously. Error: ${it.error.message}")
            }
        }

    }
}
