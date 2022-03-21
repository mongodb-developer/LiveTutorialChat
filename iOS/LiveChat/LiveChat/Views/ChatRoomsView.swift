//
//  ChatRoomsView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI

struct ChatRoomsView: View {
    let username: String
    var preview = false
    
    let rooms = ["Java", "Kotlin", "Swift", "JavaScript", "Python"]
    
    var body: some View {
        List {
            ForEach(rooms, id: \.self ) { room  in
                if let realmUser = realmApp.currentUser {
                    NavigationLink (
                        destination: ChatsView(username: username, room: room)
                            .environment(\.realmConfiguration, realmUser.configuration(partitionValue: room))) {
                        Text(room)
                    }
                }
            }
        }
        .navigationBarTitle("Rooms", displayMode: .inline)
    }
}
