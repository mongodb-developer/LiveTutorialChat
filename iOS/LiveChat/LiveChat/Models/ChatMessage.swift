//
//  ChatMessage.swift
//  LiveChat
//
//  Created by Andrew Morgan on 25/03/2021.
//

import Foundation
import RealmSwift

class ChatMessage: Object, ObjectKeyIdentifiable {
    @Persisted(primaryKey: true) var _id = ObjectId.generate()
    @Persisted var room = ""
    @Persisted var author = ""
    @Persisted var text = ""
    @Persisted var timestamp = Date()

    convenience init(author: String, text: String, room: String) {
        self.init()
        self.author = author
        self.text = text
        self.room = room
    }
}
