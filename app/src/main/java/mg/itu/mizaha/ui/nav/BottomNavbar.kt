package mg.itu.mizaha.ui.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import mg.itu.mizaha.data.ServiceItem

private val BleuFonce = Color(0xFF1E243A)
private val Orange = Color(0xFFE8602D)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showQuickMenu by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 14.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { destination ->
                val selected = when (destination) {
                    Destination.Urgence -> currentRoute == Destination.Urgence.route
                    Destination.Carte -> currentRoute == Destination.Carte.route
                    else -> currentRoute != Destination.Urgence.route &&
                            currentRoute != Destination.Carte.route
                }

                BottomNavItem(
                    destination = destination,
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLongPress = {
                        if (destination.quickServices != null) showQuickMenu = true
                    }
                )
            }
        }

        // ── Popup hissé ici, en dehors du Row : ne perturbe plus SpaceEvenly ──
        if (showQuickMenu) {
            Popup(
                alignment = Alignment.TopCenter,
                offset = with(LocalDensity.current) { IntOffset(0, (-190).dp.roundToPx()) },  // ← augmenté
                onDismissRequest = { showQuickMenu = false },
                properties = PopupProperties(focusable = true)
            ) {
                QuickServicesMenu(
                    services = Destination.Accueil.quickServices ?: emptyList(),
                    onSelect = { route ->
                        showQuickMenu = false
                        navController.navigate(route)
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    destination: Destination,
    selected: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    val cercleSize = if (selected) 56.dp else 44.dp
    val iconSize = if (selected) 50.dp else 34.dp
    val offsetY = if (selected) (-14).dp else 0.dp

    Box(
        modifier = Modifier
            .offset(y = offsetY)
            .then(
                if (selected) Modifier.shadow(elevation = 6.dp, shape = CircleShape)
                else Modifier
            )
            .size(cercleSize)
            .clip(CircleShape)
            .background(if (selected) destination.selectedColor else Color.Transparent)
            .pointerInput(destination) {
                detectTapGestures(
                    onTap = { onClick() },   // ← toujours appelé, jamais conditionné par `selected`
                    onLongPress = { onLongPress() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = destination.iconRes),
            contentDescription = destination.label,
            modifier = Modifier.size(iconSize),
            colorFilter = ColorFilter.tint(if (selected) Color.White else BleuFonce.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun QuickServicesMenu(
    services: List<ServiceItem>,
    onSelect: (String) -> Unit
) {
    val ligneHaut = services.take(3)
    val ligneBas = services.drop(3)

    Card(
        modifier = Modifier
            .fillMaxWidth(0.92f),   // ← plus large qu'avant (0.9f)
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ligneHaut.forEach { service ->
                    ServiceMiniItem(service, onSelect)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                // Espace entre les 2 items du bas, centrés
            ) {
                ligneBas.forEachIndexed { index, service ->
                    if (index > 0) Spacer(Modifier.width(32.dp))
                    ServiceMiniItem(service, onSelect)
                }
            }
        }
    }
}

@Composable
private fun ServiceMiniItem(service: ServiceItem, onSelect: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onSelect(service.route) }
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = service.iconRes),
            contentDescription = service.label,
            modifier = Modifier.size(36.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = service.label,
            fontSize = 10.sp,
            color = BleuFonce,
            maxLines = 1
        )
    }
}