package fr.mijieux.mesmoutons.ui.sheepdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.mijieux.mesmoutons.MyApplication
import fr.mijieux.mesmoutons.core.Database
import fr.mijieux.mesmoutons.core.Mouton
import kotlinx.coroutines.launch

class SheepDetailsViewModel(private val app: Application, private val idMouton: Long) : AndroidViewModel(app) {

    val dao = Database.getInstance(app.applicationContext).moutonDao()
    val moutonData = dao.getMouton(idMouton)

    class SheepDetailsViewModelFactory(private val idMouton: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SheepDetailsViewModel(MyApplication.instance, idMouton) as T
        }
    }

    public fun deleteMouton() {
        viewModelScope.launch {
            dao.delete(idMouton)
        }
    }

}