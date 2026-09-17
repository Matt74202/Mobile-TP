package mg.itu.mizaha.data

import androidx.compose.ui.graphics.Color
import mg.itu.mizaha.R

data class ServiceItem(
    val label: String,
    val iconRes: Int,
    val accentColor: Color,
    val route: String
)

val services = listOf(
    ServiceItem("Restaurants", R.drawable.ic_resto, Color(0xFFE8602D), "restaurants"),
    ServiceItem("Hôtels", R.drawable.ic_hotel, Color(0xFF51A5C7), "hotels"),
    ServiceItem("Banques", R.drawable.ic_banque, Color(0xFF1E243A), "banques"),
    ServiceItem("Pharmacies", R.drawable.ic_pharmacie, Color(0xFF1A90A0), "pharmacies"),
    ServiceItem("Activités", R.drawable.ic_activite, Color(0xFFE67E22), "activites")
)