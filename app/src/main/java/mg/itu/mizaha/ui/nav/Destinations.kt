package mg.itu.mizaha.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String, val label: String, val icon: ImageVector) {
    data object Accueil : Destination("accueil", "Accueil", Icons.Filled.Home)
    data object Urgence : Destination("urgence", "Urgence", Icons.Filled.Warning)
    data object Autre : Destination("autre", "Plus", Icons.Filled.MoreHoriz)
}

val bottomNavItems = listOf(Destination.Accueil, Destination.Urgence, Destination.Autre)