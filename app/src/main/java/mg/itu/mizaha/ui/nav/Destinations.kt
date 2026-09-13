package mg.itu.mizaha.ui.nav

import androidx.compose.ui.graphics.Color
import mg.itu.mizaha.R
import mg.itu.mizaha.data.ServiceItem
import mg.itu.mizaha.data.services

private val BleuFonce = Color(0xFF1E243A)
private val Rouge = Color(0xFFE53E3E)

sealed class Destination(
    val route: String,
    val label: String,
    val iconRes: Int,
    val selectedColor: Color = BleuFonce,
    val quickServices: List<ServiceItem>? = null
) {
    data object Accueil : Destination(
        route = "accueil",
        label = "Accueil",
        iconRes = R.drawable.ic_nav_accueil,
        quickServices = services
    )

    data object Urgence : Destination(
        route = "urgence",
        label = "Urgence",
        iconRes = R.drawable.ic_nav_urgence,
        selectedColor = Rouge
    )

    data object Autre : Destination(
        route = "autre",
        label = "Plus",
        iconRes = R.drawable.ic_nav_plus
    )
}

val bottomNavItems = listOf(Destination.Urgence, Destination.Accueil, Destination.Autre)