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
                .onSubmit() {
                    Task {
                        await userAction()
                    }
                }
                .submitLabel(.go)
            Button(action: { newUser.toggle() }) {
                HStack {
                    Image(systemName: newUser ? "checkmark.square" : "square")
                    Text("Register new user")
                    Spacer()
                }
            }
            Button(newUser ? "Register new user" : "Log in") {
                Task {
                    await userAction()
                }
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
    
    func userAction() async {
        if newUser {
            await signup()
        } else {
            await login()
        }
    }
    
    private func signup() async {
        do {
            try await app.emailPasswordAuth.registerUser(email: email, password: password)
            await login()
        } catch {
            print(error.localizedDescription)
        }
    }
  
    private func login() async {
        do {
            let _ = try await app.login(credentials: .emailPassword(email: email, password: password))
            username = email
        } catch {
            print(error.localizedDescription)
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(username: .constant("Billy"))
    }
}
