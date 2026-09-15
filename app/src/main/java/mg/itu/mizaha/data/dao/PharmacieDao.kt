package mg.itu.mizaha.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mg.itu.mizaha.data.entities.Pharmacie
@Dao
interface PharmacieDao {
    @Query("SELECT * FROM pharmacies")
    fun getAll(): Flow<List<Pharmacie>>

    @Insert
    suspend fun insererTous(pharmacies: List<Pharmacie>)
}