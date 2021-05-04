package com.mongodb.realm.mongodblivechat.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class ChatMessage (rm : String, user: String, msg : String ): RealmObject() {
    @PrimaryKey
    var _id: ObjectId? = ObjectId()
    var _partition: String? = rm //This is the chat room TODO: change to chat room
    var author : String? = user
    var text: String? = msg
    var timestamp: Date? = Date()

   constructor () : this(rm = "public", user = "anonymous", msg = "test message") {
        _id = ObjectId()
        timestamp = Date()
    }

    fun getAuthorName() : String {
        val atPos = author!!.indexOf("@")

        return author!!.subSequence(0, atPos).toString()
    }
}