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
        Text("Loggin in")
            .onAppear(perform: anonymousLogin)
    }
    
    
    func anonymousLogin() {
        app.login(credentials: .anonymous) { result in
            DispatchQueue.main.async {
                switch result {
                case .failure(let error):
                    print(error.localizedDescription)
                case .success(let user):
                    print(user.id)
                    username = user.id
                }
            }
        }
    }
}

struct AnonymousLoginView_Previews: PreviewProvider {
    static var previews: some View {
        AnonymousLoginView(username: .constant("Andrew"))
    }
}
