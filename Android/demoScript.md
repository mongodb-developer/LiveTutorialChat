
Steps

  1.  Navigation Map
		* three screens
		* actions
		* action arguments
		* show XML
  2.  MainActivity
		* Step 0a - *Initialize Realm*
  3.  LiveChatApplication
		* Step 0b - *Connect to the Realm backend*
  4.  Define ChatMessage RealmObj
		* Step 1 - Define ChatMessage Class
  5.  Login Fragment
		* fragment_login.xml
		* LoginFragment.kt
		* Step 2 - Complete loginButton onclick listener
  6.  ChatRoomSelect
        * fragment_chat_room_select.xml
		* ChatFragment.kt
  7.  ChatFragment
	    * chat_fragment.xml
		* ChatFragment.kt
		* ChatModel.kt
		* Step 3a - *Configure Realm*
		* Step 3b - *Open the Realm*
		* Step 4a - Populate _chatMessages with the ChatMessages in the realm (chat room)
		* Step 4b - Update chat history text box based upon ChatMessages
		* ChatFragment.kt
	    * Step 4c - Set up observer for ChatMessage list changes		



[Step 0] MainActivity.kt
================================================================

```
//TODO: [Step 0a] - Initialize Realm
Realm.init(this) // context, usually an Activity or Application
```

```
//TODO: [Step 0b] - connect to the MongoDB Realm app backend
    val chatApp: App by lazy {
        App(AppConfiguration.Builder(BuildConfig.REALM_APP_ID).build())
    }
```




[Step 1] ChatMessage.kt
================================================================

```
    @PrimaryKey
    var _id: ObjectId? = ObjectId()
    var chatRoom: String = rm
    var author : String? = user
    var text: String? = msg
    var timestamp: Date? = Date()

   constructor () : this(rm = "public", user = "anonymous", msg = "test message") {
        _id = ObjectId()
        timestamp = Date()
    }

    fun getAuthorName() : String {
        val atPos = author!!.indexOf("@")

        return if (atPos > 0) author!!.subSequence(0, atPos).toString() else author!!
    }
```

[Step 2] LoginFragment.kt
================================================================
```
            (requireActivity().application as LiveChatApplication)
                .chatApp.loginAsync(Credentials.emailPassword(eMail, pWord)) {
                if (it.isSuccess) {
                    Log.v("QUICKSTART", "Successfully logged in Email: $eMail, Password: $pWord")

                    val action : ActionLoginFragmentToChatRoomFragment = LoginFragmentDirections.actionLoginFragmentToChatRoomFragment()
                    action.email = editTextEmailAddress.text.toString()
                    action.password = editTextPassword.text.toString()
                    Navigation.findNavController(view).navigate(action)
                } else {
                    val eMsg = "Failed to log $eMail. Error: ${it.error.message}"
                    Log.e("QUICKSTART", eMsg)
                    setErrorMsg(eMail)
                }
            }
```

[Step 3] ChatModel.kt
================================================================
```
        val config = SyncConfiguration.Builder(user!!, partitionValue)
            // because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
```
```
       this.realm = Realm.getInstance(config)
```

[Step 4ab] ChatModel.kt
================================================================
```
       _chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll().sort("timestamp"))
```
```
        setMessageHistoryText(_chatMessages!!.value!!)
```

[Step 4c] ChatFragment.kt
================================================================
```
	            val messageObserver = Observer<List<ChatMessage>?> {
                    cMessages ->
                binding.myChatModel?.setMessageHistoryText(cMessages)
                Log.v("QUICKSTART", "Updating message history list")
            }

            binding.myChatModel?._chatMessages?.observe(viewLifecycleOwner, messageObserver)
```
