package mg.itu.mizaha.data.entities

data class Pharmacie(
    val id: Int,
    val nom: String,
    val lieu: String
)

data class PharmacieDeGarde(
    val nom: String,
    val lieu: String
)

data class SemaineDeGarde(
    val dateDebut: String,   // ex: "12/09/2026"
    val dateFin: String,     // ex: "19/09/2026"
    val pharmacies: List<PharmacieDeGarde>
)