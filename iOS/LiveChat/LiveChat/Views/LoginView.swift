//
//  LoginView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI
import RealmSwift
import Combine

struct LoginView: View {
    @Binding var username: String

    @State private var email = ""
    @State private var password = ""
    @State private var newUser = false
    
    @State private var cancellables = Set<AnyCancellable>()
    
    var body: some View {
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
            Button(action: userAction) {
                Text(newUser ? "Register new user" : "Log in")
            }
            Spacer()
        }
        .padding()
    }
    
    func userAction() -> Void {
        if newUser {
            signup()
        } else {
            login()
        }
    }
    
    private func signup() {
        app.emailPasswordAuth.registerUser(email: email, password: password) { error in
            if let error = error {
                print("\(error.localizedDescription)")
            } else {
                login()
            }
        }
    }
    
    private func login() {
        app.login(credentials: .emailPassword(email: email, password: password)) { _ in
            DispatchQueue.main.async {
                username = email
            }
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(username: .constant("Billy"))
    }
}
