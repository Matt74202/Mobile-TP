package mg.itu.mizaha.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotels")
data class Hotel(
    @PrimaryKey val id: Int,
    val nom: String,
    val lieu: String,
    val latitude: Double,
    val longitude: Double,
    val contact: String,
    val siteWeb: String?,
    val imageRes: Int
)