package fr.mijieux.mesmoutons.core

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


enum class TypeMouton(val value: Int) {
    INDETERMINE(0),
    AGNEAU(1),
    BREBIS(2),
    BELIER(3);

    
    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.firstOrNull{it.value == value}
    }
}

enum class SexeMouton(val value: Int) {

    INCONNU(0),
    MALE(1),
    FEMELLE(2);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.firstOrNull{it.value == value}
    }
}

class MoutonTypeConverters {

    @TypeConverter
    fun toSexe(value: Int): SexeMouton? {
        return SexeMouton.getByValue(value)
    }

    @TypeConverter
    fun fromSexe(sexe: SexeMouton): Int {
        return sexe.value
    }
    
    @TypeConverter
    fun toType(value: Int): TypeMouton? {
        return TypeMouton.getByValue(value)
    }

    @TypeConverter
    fun fromType(Type: TypeMouton): Int {
        return Type.value
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return value?.let{ LocalDateTime.parse(it, fmt) }
    }

    @TypeConverter
    fun toTimestamp(datetime: LocalDateTime?): String? {
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return datetime?.format(fmt)
    }

}


@Entity(indices=[Index(unique=true,value=["numero"],name="uq_numero")],
        foreignKeys = arrayOf(ForeignKey(
            entity=Mouton::class,
            parentColumns= arrayOf("id"),
            childColumns= arrayOf("parentId"))))
data class Mouton (
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var numero: Long? = null,
    var nom: String? = null,
    var description: String? = null,
    var sexe: SexeMouton = SexeMouton.INCONNU,
    var parentId: Long? = null,
    var type: TypeMouton = TypeMouton.INDETERMINE,
    var dateNaissance: LocalDateTime? = null,
    var dateMort: LocalDateTime? = null,
    var dateAchat: LocalDateTime? = null,
    var dateVente: LocalDateTime? = null,
    var prixAchat: Float? = null,
    var prixVente: Float? = null,
    var nomAcheteur: String? = null,
    var nomVendeur: String? = null
)

data class MoutonWithMotherAndChild(
    @Embedded val mouton: Mouton,
    @Relation(
        parentColumn="id",
        entityColumn="parentId",
    ) val mother: Mouton?,
    @Relation(
        parentColumn="parentId",
        entityColumn="id",
    ) val child: List<Mouton>
)

@Dao
interface MoutonDao {
    @Query("SELECT * FROM mouton WHERE id = :idMouton")
    fun getMouton(idMouton: Long): LiveData<Mouton>

    @Transaction
    @Query("SELECT * FROM mouton")
    fun getAll(): LiveData<List<MoutonWithMotherAndChild>>

    @Query("SELECT * FROM mouton WHERE dateMort IS NULL AND dateVente IS NULL")
    fun getAllPresent(): LiveData<List<Mouton>>

    @Query("SELECT * FROM mouton WHERE dateMort IS NOT NULL")
    fun getAllDead(): LiveData<List<Mouton>>


    @Query("SELECT * FROM mouton WHERE dateVente IS NOT NULL")
    fun getAllSold(): LiveData<List<Mouton>>

    @Query("SELECT * FROM mouton WHERE parentId = :parentId")
    fun getAllChildren(parentId: Int): LiveData<List<Mouton>>

    @Query("SELECT * FROM mouton WHERE sexe = :genre")
    fun getAllGenre(genre: Int): LiveData<List<Mouton>>

    @Insert
    suspend fun insertAll(vararg mouton: Mouton)

    @Delete
    suspend fun delete(mouton: Mouton)

    @Query("DELETE FROM mouton WHERE id = :idMouton")
    suspend fun delete(idMouton: Long)

    @Update
    suspend fun update(mouton: Mouton)


    @Query("DELETE FROM mouton")
    suspend fun deleteAll()
}

@Database(entities = arrayOf(Mouton::class), version = 1)
@TypeConverters(MoutonTypeConverters::class)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun moutonDao(): MoutonDao
}