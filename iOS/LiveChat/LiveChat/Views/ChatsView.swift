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
    
    @FocusState var isTextFocussed: Bool
    
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
                    .padding(4)
                }
            }
            Spacer()
            HStack {
                TextField("New message", text: $message)
                    .padding(8)
                    .background(Color.yellow)
                    .clipShape(Capsule())
                    .focused($isTextFocussed)
                    .onSubmit(send)
                    .submitLabel(.send)
                Button(action: send) { Image(systemName: "paperplane.fill") }
            }
        }
        .navigationBarTitle("\(room) messages", displayMode: .inline)
        .padding()
    }
    
    func send() {
        $chats.append(ChatMessage(author: username, text: message, room: room))
        message = ""
        isTextFocussed = true
    }
}

struct ChatsView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ChatsView(username: "billy@fish.com", room: "Test room")
        }
    }
}
