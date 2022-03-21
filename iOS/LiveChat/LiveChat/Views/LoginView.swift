//
//  LoginView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI
import RealmSwift

struct LoginView: View {
    @Binding var username: String

    @State private var email = ""
    @State private var password = ""
    @State private var newUser = false
    @State private var errorMessage = ""
    @State private var busy = false
    
    var body: some View {
        ZStack {
            VStack(spacing: 16) {
                Spacer()
                TextField("email address", text: $email)
                SecureField("password", text: $password)
                Button(action: { newUser.toggle() }) {
                    HStack {
                        Image(systemName: newUser ? "checkmark.square" : "square")
                        Text("Register new user")
                        Spacer()
                    }
                }
                Button(action: login) {
                    Text(newUser ? "Register new user" : "Log in")
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
                .disabled(email == "" || password == "" || busy)
                Spacer()
                Text(errorMessage)
                    .foregroundColor(.red)
            }
            .padding()
            if busy {
                ProgressView()
            }
        }
    }
    
    func login() {
        busy = true
        errorMessage = ""
        Task {
            do {
                if newUser {
                    try await realmApp.emailPasswordAuth.registerUser(email: email, password: password)
                }
                let _ = try await realmApp.login(credentials: .emailPassword(email: email, password: password))
                username = email
                busy = false
            } catch {
                errorMessage = error.localizedDescription
                busy = false
            }
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(username: .constant("Billy"))
    }
}
