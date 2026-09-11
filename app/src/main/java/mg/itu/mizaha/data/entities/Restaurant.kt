package mg.itu.mizaha.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey val id: Int,
    val nom: String,
    val adresse: String,        // Quartier
    val latitude: Double,
    val longitude: Double,
    val type: String,           // Français, Malgache, etc.
    val telephone: String = "",
    val note: Double? = null
)