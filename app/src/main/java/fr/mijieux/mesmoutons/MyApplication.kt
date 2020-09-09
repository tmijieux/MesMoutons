package fr.mijieux.mesmoutons

import android.app.Application

class MyApplication : Application() {

    companion object {
        private lateinit var m_instance: MyApplication
        val instance: MyApplication
            get() = m_instance
    }

    override fun onCreate() {
        super.onCreate()
        m_instance = this
    }

}