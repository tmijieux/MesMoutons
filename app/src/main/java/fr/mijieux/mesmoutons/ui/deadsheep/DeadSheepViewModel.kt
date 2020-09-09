package fr.mijieux.mesmoutons.ui.deadsheep

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.mijieux.mesmoutons.core.Database

class DeadSheepViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Database.getInstance(application.applicationContext)
    private val moutonDao = db.moutonDao()
    val moutons = moutonDao.getAllDead()

}