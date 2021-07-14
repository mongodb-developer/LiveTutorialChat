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
        NavigationView {
            Group {
                if app.currentUser == nil {
                    LoginView(username: $username)
                } else {
                    ChatRoomsView(username: username)
                }
            }
            .navigationBarTitle(username, displayMode: .inline)
            .navigationBarItems(trailing: app.currentUser != nil ? Button(action: logout) { Text("Logout") } : nil) }
     }
    
    func logout() {
        app.currentUser?.logOut() { _ in
            DispatchQueue.main.async {
                username = ""
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
