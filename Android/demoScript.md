
Steps

  1.  Navigation Map
		* three screens
		* actions
		* action arguments
		* show XML
  2.  MainActivity
		* Step 0a - *Initialize Realm
		* Step 0b - Connect to the Realm backend
  3.  Define ChatMessage RealmObj
		* Step 1 - Define ChatMessage Class
  4.  Login Fragment
		* fragment_login.xml
		* LoginFragment.kt
		* Step 2 - Complete loginButton onclick listener


Steps
  * one
  * two
  * three



[Step 0] MainActivity.kt
================================================================

```
//TODO: [Step 0a] - Initialize Realm
Realm.init(this) // context, usually an Activity or Application
```

```
//TODO: [Step 0b] - connect to the MongoDB Realm app backend
        chatApp = App(
            AppConfiguration.Builder(BuildConfig.REALM_APP_ID)
                .build()
        )
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

[Step 2]
================================================================
