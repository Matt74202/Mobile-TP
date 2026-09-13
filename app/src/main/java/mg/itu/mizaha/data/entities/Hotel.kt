package mg.itu.mizaha.data.entities

data class Hotel(
    val id: Int,
    val nom: String,
    val lieu: String,
    val contact: String,
    val siteWeb: String?,
    val imageRes: Int
)