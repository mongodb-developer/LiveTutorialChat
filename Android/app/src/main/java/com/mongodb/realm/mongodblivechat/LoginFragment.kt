package com.mongodb.realm.mongodblivechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.navigation.Navigation
import com.mongodb.realm.mongodblivechat.LoginFragmentDirections.ActionLoginFragmentToChatRoomFragment
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.fragment_login.*
import com.mongodb.realm.mongodblivechat.R.string.failedLoginMessage as failedLoginMessage



/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //This listener should perform the following tasks
        //Use the email and password entered into the respective text boxes to authenticate the user
        // - If authentication is successful, then the UI should navigate to the ChatRoomSelect fragment
        // - If authentication fails, then an error message should be displayed and the UI should not switch screens
        loginButton.setOnClickListener{
            val eMail = editTextEmailAddress.text.toString()
            val pWord = editTextPassword.text.toString()

            val view = it
            //TODO: [STEP 2] - Complete loginButton onclick listener

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun setErrorMsg(email : String) {
        val errMsg : String = getString(failedLoginMessage, email)
        errorMsgBox.text = errMsg
    }
}