package mg.itu.mizaha.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mg.itu.mizaha.data.entities.Restaurant

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurants ORDER BY nom ASC")
    fun getAll(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun byId(id: Int): Restaurant?

    @Query("SELECT * FROM restaurants WHERE type = :type")
    fun byType(type: String): Flow<List<Restaurant>>

    @Insert
    suspend fun insererTous(restaurants: List<Restaurant>)
}