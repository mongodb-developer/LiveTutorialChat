//
//  ChatMessage.swift
//  LiveChat
//
//  Created by Andrew Morgan on 25/03/2021.
//

import Foundation
import RealmSwift

@objcMembers class ChatMessage: Object, ObjectKeyIdentifiable {
    dynamic var _id = ObjectId.generate()
    dynamic var room = ""
    dynamic var author = ""
    dynamic var text = ""
    dynamic var timestamp = Date()
    
    override static func primaryKey() -> String? {
        return "_id"
    }
}
