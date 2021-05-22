package com.mongodb.realm.mongodblivechat.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.navigation.Navigation
import com.mongodb.realm.mongodblivechat.LiveChatApplication
import com.mongodb.realm.mongodblivechat.ui.login.LoginFragmentDirections.ActionLoginFragmentToChatRoomFragment
import com.mongodb.realm.mongodblivechat.R
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.fragment_login.*
import com.mongodb.realm.mongodblivechat.R.string.failedLoginMessage as failedLoginMessage



/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO: [STEP 2] - Complete code for loginButton on click listener action
        //This listener should perform the following tasks
        //Use the email and password entered into the respective text boxes to authenticate the user
        // - If authentication is successful, then the UI should navigate to the ChatRoomSelect fragment
        // - If authentication fails, then an error message should be displayed and the UI should not switch screens
        loginButton.setOnClickListener{
            val eMail = editTextEmailAddress.text.toString()
            val pWord = editTextPassword.text.toString()

            val view = it

            //TODO: [STEP 2] - Complete loginButton onclick listener
            (requireActivity().application as LiveChatApplication)
                .chatApp.loginAsync(Credentials.emailPassword(eMail, pWord)) {
                if (it.isSuccess) {
                    Log.v("QUICKSTART", "Successfully logged in Email: $eMail, Password: $pWord")

                    val action : ActionLoginFragmentToChatRoomFragment =
                        com.mongodb.realm.mongodblivechat.ui.login.LoginFragmentDirections.actionLoginFragmentToChatRoomFragment()
                    action.email = editTextEmailAddress.text.toString()
                    action.password = editTextPassword.text.toString()
                    Navigation.findNavController(view).navigate(action)
                } else {
                    val eMsg = "Failed to log $eMail. Error: ${it.error.message}"
                    Log.e("QUICKSTART", eMsg)
                    setErrorMsg(eMail)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun setErrorMsg(email : String) {
        val errMsg : String = getString(failedLoginMessage, email)
        errorMsgBox.text = errMsg
    }
}