package com.mongodb.realm.livedataquickstart.model

import io.realm.MutableRealmInteger
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.time.Instant.now
import java.util.*

open class ChatMessage (rm : String, user: String, msg : String ): RealmObject() {
    @PrimaryKey
    private var _id = ObjectId()
    private var _partition = rm //This is the chat room TODO: change to chat room
    private var author = user
    private var text = msg
    private var timestamp: Date = Date()

    public constructor () : this(rm = "public", user = "anonymous", msg = "test message") {
        _id = ObjectId()
        timestamp = Date()
    }
}