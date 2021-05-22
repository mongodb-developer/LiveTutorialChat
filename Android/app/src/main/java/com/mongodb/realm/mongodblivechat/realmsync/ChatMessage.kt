package com.mongodb.realm.mongodblivechat.realmsync

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*


open class ChatMessage (rm : String, user: String, msg : String ): RealmObject() {
    // TODO: [Step 1] - Define ChatMessage Class
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
}