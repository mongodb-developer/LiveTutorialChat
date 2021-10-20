//
//  ContentView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI

struct ContentView: View {
    @State private var username = ""
    
    var body: some View {
        return NavigationView {
            Group {
                if username == "" {
                    LoginView(username: $username)
                } else {
                    ChatRoomsView(username: username)
                }
            }
            .navigationBarTitle(username, displayMode: .inline)
            .navigationBarItems(trailing: username != "" ? LogoutButton(username: $username) : nil) }
     }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
