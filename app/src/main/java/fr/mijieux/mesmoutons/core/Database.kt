package fr.mijieux.mesmoutons.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


class Database {

    companion object {
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder<AppDatabase>(context, AppDatabase::class.java, "mouton_db")
                    .addCallback(MoutonDatabaseCallback())
                    .build()
            }
            return instance
        }
    }
}


private class MoutonDatabaseCallback : RoomDatabase.Callback()  {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }

}
