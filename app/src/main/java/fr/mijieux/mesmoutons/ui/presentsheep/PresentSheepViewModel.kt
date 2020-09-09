package fr.mijieux.mesmoutons.ui.presentsheep

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.mijieux.mesmoutons.core.Database
import fr.mijieux.mesmoutons.core.Mouton
import fr.mijieux.mesmoutons.core.SexeMouton
import fr.mijieux.mesmoutons.core.TypeMouton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class PresentSheepViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Database.getInstance(application.applicationContext)
    private val moutonDao = db.moutonDao()
    val moutons: LiveData<List<Mouton>> = moutonDao.getAllPresent()

//    init {
//        val scope = CoroutineScope(Dispatchers.IO)
//        scope.launch {
//            moutonDao.deleteAll()
//            moutonDao
//                .insertAll(
//                    Mouton(id=1,numero=10,type= TypeMouton.BREBIS, sexe= SexeMouton.FEMELLE),
//                    Mouton(id=2,numero=11,type= TypeMouton.BREBIS, sexe= SexeMouton.FEMELLE),
//                    Mouton(id=3,numero=12,type= TypeMouton.AGNEAU, sexe= SexeMouton.MALE, parentId=2),
//                    Mouton(id=4,numero=13,type= TypeMouton.AGNEAU, sexe= SexeMouton.FEMELLE,parentId=2),
//                    Mouton(id=5,numero=14,type= TypeMouton.AGNEAU, sexe= SexeMouton.FEMELLE,parentId=2, dateMort=LocalDateTime.now()),
//                    Mouton(id=6,numero=15,type= TypeMouton.AGNEAU, sexe= SexeMouton.FEMELLE,parentId=2, dateVente=LocalDateTime.now())
//                )
//        }
//    }

}