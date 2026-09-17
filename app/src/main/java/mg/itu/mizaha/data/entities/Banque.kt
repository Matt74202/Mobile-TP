package mg.itu.mizaha.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banques")
data class Banque(
    @PrimaryKey val id: Int,
    val nom: String,
    val enseigne: String,
    val lieu: String,
    val latitude: Double,
    val longitude: Double
)