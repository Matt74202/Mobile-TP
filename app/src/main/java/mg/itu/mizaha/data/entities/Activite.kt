package mg.itu.mizaha.data.entities

data class Activite(
    val id: Int,
    val nomLieu: String,      // ex: "Canal Olympia Andohatapenaka"
    val nomActivite: String,  // ex: "Cinéma"
    val type: String,         // ex: "Détente", "Sport", "Culture"
    val quartier: String,
    val latitude: Double,
    val longitude: Double
)