//
//  PrettyTextView.swift
//  LiveChat
//
//  Created by Andrew Morgan on 18/06/2021.
//

import SwiftUI

struct PrettyTextView: View {
    @State var newMessage = ""
    
    var body: some View {
        HStack {
            TextField("message", text: $newMessage)
                .padding(8)
                .background(Color.yellow)
                .clipShape(Capsule())
                
            Button(action: {}) {
                Image(systemName: "paperplane.fill")
            }
        }
    }
}

struct PrettyTextView_Previews: PreviewProvider {
    static var previews: some View {
        PrettyTextView()
            .padding()
    }
}
