//
//  LogoutButton.swift
//  LogoutButton
//
//  Created by Andrew Morgan on 13/09/2021.
//

import SwiftUI

struct LogoutButton: View {
    @Binding var username: String
    
    @State private var isConfirming = false
    
    var body: some View {
        Button("Logout") { isConfirming = true }
        .confirmationDialog("Are you that you want to logout",
                            isPresented: $isConfirming) {
            Button(action: logout) {
                Text("Confirm Logout")
            }
            Button("Cancel", role: .cancel) {}
        }
    }
                     
    private func logout() {
        Task {
            do {
                try await app.currentUser?.logOut()
                username = ""
            } catch {
                print("Failed to logout: \(error.localizedDescription)")
            }
        }
    }
}

struct LogoutButton_Previews: PreviewProvider {
    static var previews: some View {
        LogoutButton(username: .constant("andrew"))
    }
}
