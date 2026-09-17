package mg.itu.mizaha.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mg.itu.mizaha.data.entities.Banque

@Dao
interface BanqueDao {
    @Query("SELECT * FROM banques")
    fun getAll(): Flow<List<Banque>>

    @Insert
    suspend fun insererTous(banques: List<Banque>)
}