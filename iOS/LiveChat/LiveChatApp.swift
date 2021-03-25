//
//  LiveChatApp.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI
import RealmSwift

let app = RealmSwift.App(id: "livetutorial-xxxxx") // TODO: Use App ID from the Realm UI

@main
struct LiveChatApp: SwiftUI.App {   
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
