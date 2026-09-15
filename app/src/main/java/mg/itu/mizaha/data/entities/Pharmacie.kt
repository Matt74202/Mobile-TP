package mg.itu.mizaha.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacies")
data class Pharmacie(
    @PrimaryKey val id: Int,
    val nom: String,
    val lieu: String,
    val latitude: Double,
    val longitude: Double
)

// PharmacieDeGarde / SemaineDeGarde restent des data class simples (pas de persistance nécessaire)
data class PharmacieDeGarde(val nom: String, val lieu: String)
data class SemaineDeGarde(
    val dateDebut: String,
    val dateFin: String,
    val pharmacies: List<PharmacieDeGarde>
)