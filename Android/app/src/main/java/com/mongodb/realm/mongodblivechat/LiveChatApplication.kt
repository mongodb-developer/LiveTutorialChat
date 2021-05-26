package com.mongodb.realm.mongodblivechat

import android.app.Application
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

class LiveChatApplication : Application() {

    //TODO: [Step 0b] - connect to the MongoDB Realm app backend
    val chatApp: App by lazy {
        App(AppConfiguration.Builder(BuildConfig.REALM_APP_ID).build())
    }

    override fun onCreate() {
        super.onCreate()


    }

}