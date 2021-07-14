//
//  ChatRoomsView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI

struct ChatRoomsView: View {
    let username: String
    
    let rooms = ["Java", "Kotlin", "Swift", "JavaScript"]
    
    var body: some View {
        List {
            ForEach(rooms, id: \.self) { room in
                NavigationLink(
                    destination: ChatView(username: username, room: room)
                        .environment(\.realmConfiguration, app.currentUser!.configuration(partitionValue: room))
                ) { Text(room)}
            }
        }
        .navigationBarTitle("Chat Rooms", displayMode: .inline)
    }
}
