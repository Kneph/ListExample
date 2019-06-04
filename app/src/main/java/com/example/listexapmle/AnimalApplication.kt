package com.example.listexapmle

import android.app.Application
import io.realm.Realm

class AnimalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}