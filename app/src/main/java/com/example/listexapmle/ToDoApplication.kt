package com.example.listexapmle

import android.app.Application
import io.realm.Realm

class ToDoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}