package mg.itu.mizaha.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mg.itu.mizaha.data.entities.Hotel

@Dao
interface HotelDao {
    @Query("SELECT * FROM hotels")
    fun getAll(): Flow<List<Hotel>>

    @Insert
    suspend fun insererTous(hotels: List<Hotel>)
}


