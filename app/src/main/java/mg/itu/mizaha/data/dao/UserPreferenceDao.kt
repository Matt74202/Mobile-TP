package mg.itu.mizaha.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mg.itu.mizaha.data.entities.UserPreference
@Dao
interface UserPreferenceDao {
    @Query("SELECT * FROM user_preferences")
    fun getAll(): Flow<List<UserPreference>>

    @Query("UPDATE user_preferences SET score = score + :delta WHERE categorie = :categorie")
    suspend fun incrementer(categorie: String, delta: Float)

    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    suspend fun creerSiAbsent(pref: UserPreference)
}