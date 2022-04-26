package uz.gita.contacts.data.source.local

import android.content.Context

class SharedPref(context: Context) {

    companion object {

        private var instance: SharedPref? = null

        fun init(context: Context) {
            instance = SharedPref(context)
        }

        fun getInstance(): SharedPref = instance!!

    }

    private val pref = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)



    var token: String
        set(value) = pref.edit().putString("TOKEN", value).apply()
        get() = pref.getString("TOKEN", "")!!

    var base_url: String
        set(value) = pref.edit().putString("BASE_URL", value).apply()
        get() = pref.getString("BASE_URL", "https://com.example.app/")!!


    var isLogedIn: Boolean
        set(value) = pref.edit().putBoolean("IS_LOGGED_IN", value).apply()
        get() = pref.getBoolean("IS_LOGGED_IN", false)

}