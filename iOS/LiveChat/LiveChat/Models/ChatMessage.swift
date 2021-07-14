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
}
