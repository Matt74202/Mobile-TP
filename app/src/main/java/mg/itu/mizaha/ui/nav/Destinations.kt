package mg.itu.mizaha.ui.nav

import mg.itu.mizaha.R

sealed class Destination(
    val route: String,
    val label: String,
    val iconRes: Int          // ← changé de ImageVector → Int
) {
    data object Accueil : Destination(
        route = "accueil",
        label = "Accueil",
        iconRes = R.drawable.ic_nav_accueil          // ← ton image
    )

    data object Urgence : Destination(
        route = "urgence",
        label = "Urgence",
        iconRes = R.drawable.ic_nav_urgence          // ← ton image
    )

    data object Autre : Destination(
        route = "autre",
        label = "Plus",
        iconRes = R.drawable.ic_nav_plus             // ← ton image
    )
}

// Ordre = ordre d'affichage. Accueil au milieu.
val bottomNavItems = listOf(Destination.Urgence, Destination.Accueil, Destination.Autre)