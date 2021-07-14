//
//  ContentView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI

struct ContentView: View {
    @State var username = ""
    
    var body: some View {
        NavigationView {
            if username == "" {
                AnonymousLoginView(username: $username)
            } else {
                ChatRoomsView(username: username)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
