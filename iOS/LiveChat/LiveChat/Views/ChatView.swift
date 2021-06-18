//
//  ChatView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 18/06/2021.
//

import SwiftUI
import RealmSwift

struct ChatView: View {
    @ObservedResults(ChatMessage.self, sortDescriptor: SortDescriptor(keyPath: "timestamp", ascending: true)) var messages
    
    let username: String
    let room: String
    
    @State var newMessage = ""
    
    var body: some View {
        VStack {
            ScrollView {
                ForEach(messages) { message in
                    HStack {
                        if message.author == username { Spacer() }
                        Text(message.text)
                        if message.author != username { Spacer() }
                    }
                    .padding(2)
                }
            }
            Spacer()
            HStack {
                TextField("message", text: $newMessage)
                    .padding(8)
                    .background(Color.yellow)
                    .clipShape(Capsule())
                Button(action: send) {
                    Image(systemName: "paperplane.fill")
                }
            }
        }
        .navigationBarTitle("\(room) messages")
        .padding()
    }
    
    func send() {
        let chat = ChatMessage()
        chat.author = username
        chat.text = newMessage
        chat.room = room
        $messages.append(chat)
        newMessage = ""
    }
}

