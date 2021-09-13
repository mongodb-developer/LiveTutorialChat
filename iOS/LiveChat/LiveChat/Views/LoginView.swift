//
//  LoginView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 24/03/2021.
//

import SwiftUI
import RealmSwift

struct LoginView: View {
    
    enum Field: Hashable {
        case username
        case password
    }
    
    @Binding var username: String

    @State private var email = ""
    @State private var password = ""
    @State private var newUser = false
    
    @FocusState private var focussedField: Field?
    
    var body: some View {
        VStack(spacing: 16) {
            Spacer()
            TextField("email address", text: $email)
                .focused($focussedField, equals: .username)
                .submitLabel(.next)
                .onSubmit { focussedField = .password }
            SecureField("password", text: $password)
                .focused($focussedField, equals: .password)
                .onSubmit(userAction)
                .submitLabel(.go)
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
            .buttonStyle(.borderedProminent)
            .controlSize(.large)
            Spacer()
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                focussedField = .username
            }
        }
        .padding()
    }
    
    func userAction() {
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
        app.login(credentials: .emailPassword(email: email, password: password)) { result in
            DispatchQueue.main.async {
                switch result {
                case .failure(let error):
                    print(error.localizedDescription)
                case .success(_):
                    username = email
                }
            }
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(username: .constant("Billy"))
    }
}
