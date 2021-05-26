//
//  ChatsView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 25/03/2021.
//

import SwiftUI
import RealmSwift

struct ChatsView: View {
    @ObservedResults(ChatMessage.self, sortDescriptor: SortDescriptor(keyPath: "timestamp", ascending: true)) var chats
    
    let username: String
    let room: String
    
    @State private var message = ""
    
    var body: some View {
        VStack {
            ScrollView(.vertical) {
                ForEach(chats) { chatMessage in
                    HStack {
                        if chatMessage.author == username { Spacer() }
                        Text(chatMessage.text)
                        if chatMessage.author != username { Spacer() }
                    }
                }
            }
            Spacer()
            HStack {
                TextField("New message", text: $message)
                Button(action: send) { Image(systemName: "paperplane.fill") }
            }
        }
        .navigationBarTitle("\(room) messages", displayMode: .inline)
        .padding()
    }
    
    func send() {
        $chats.append(ChatMessage(author: username, text: message, room: room))
        message = ""
    }
}

struct ChatsView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ChatsView(username: "billy@fish.com", room: "Test room")
        }
    }
}
