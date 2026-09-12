package mg.itu.mizaha.ui.nav

import androidx.compose.ui.graphics.Color
import mg.itu.mizaha.R

private val BleuPrincipal = Color(0xFF1E243A)
private val Rouge = Color(0xFFE53E3E)

sealed class Destination(
    val route: String,
    val label: String,
    val iconRes: Int,
    val selectedColor: Color = BleuPrincipal
) {
    data object Urgence : Destination("urgence", "Urgence", R.drawable.ic_nav_urgence, selectedColor = Rouge)
    data object Accueil : Destination("accueil", "Accueil", R.drawable.ic_nav_accueil)
    data object Autre : Destination("autre", "Plus", R.drawable.ic_nav_plus)
}

val bottomNavItems = listOf(Destination.Urgence, Destination.Accueil, Destination.Autre)