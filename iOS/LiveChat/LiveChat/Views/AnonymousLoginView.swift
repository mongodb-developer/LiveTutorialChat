//
//  AnonymousLoginView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 18/06/2021.
//

import SwiftUI

struct AnonymousLoginView: View {
    @Binding var username: String
    
    var body: some View {
        Text("Logging in")
            .onAppear {
                Task {
                    anonymousLogin()
                }
            }
    }
    
    func anonymousLogin() {
        Task {
            do {
                let user = try await app.login(credentials: .anonymous)
                username = user.id
            } catch {
                print(error.localizedDescription)
            }
        }
    }
}

struct AnonymousLoginView_Previews: PreviewProvider {
    static var previews: some View {
        AnonymousLoginView(username: .constant("Andrew"))
    }
}
