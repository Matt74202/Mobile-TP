package mg.itu.mizaha.data.entities

data class ContactUrgence(
    val id: Int,
    val nom: String,        // ex: "Commissariat Analakely"
    val type: String,       // "Police" ou "Gendarmerie"
    val quartier: String,
    val telephone: String
)