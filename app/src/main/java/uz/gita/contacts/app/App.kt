package uz.gita.contacts.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import uz.gita.contacts.data.source.local.SharedPref

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        instance = this

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
            private set
    }

}