package com.example.friender2

import android.app.Application

class FrienderApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        application = this
    }

    companion object {
        lateinit var application: Application
    }
}