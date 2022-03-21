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
    
    @FocusState private var focussedField: Field?
    
    var body: some View {
        ZStack {
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
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                focussedField = .username
            }
        }
    }
    
    func userAction() {
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
    
    enum Field: Hashable {
        case username
        case password
    }

}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(username: .constant("Billy"))
    }
}
