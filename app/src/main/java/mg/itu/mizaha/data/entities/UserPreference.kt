package mg.itu.mizaha.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreference(
    @PrimaryKey val categorie: String, // ex: "restaurants", "Chinois", "Grill"
    val score: Float = 0f              // incrémenté à chaque interaction/visite
)