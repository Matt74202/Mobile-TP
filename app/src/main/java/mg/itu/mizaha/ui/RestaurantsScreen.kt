package mg.itu.mizaha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.Restaurant

// Couleurs d’origine
private val BleuFonce = Color(0xFF1E243A)
private val Orange = Color(0xFFE8602D)
private val BleuCiel = Color(0xFF51A5C7)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)

@Composable
fun RestaurantsScreen(restaurants: List<Restaurant>) {
    var searchNom by remember { mutableStateOf("") }
    var typeFiltre by remember { mutableStateOf("Tous") }
    var quartierFiltre by remember { mutableStateOf("") }
    var showQuartierSuggestions by remember { mutableStateOf(false) }

    val types = listOf("Tous") + restaurants.map { it.type }.distinct().sorted()
    val quartiers = restaurants.map { it.adresse }.distinct().sorted()
    val quartiersFiltres = if (quartierFiltre.isNotEmpty()) {
        quartiers.filter { it.startsWith(quartierFiltre, ignoreCase = true) }
    } else quartiers

    val restaurantsFiltres = restaurants.filter { r ->
        val matchNom = r.nom.contains(searchNom, ignoreCase = true)
        val matchType = typeFiltre == "Tous" || r.type == typeFiltre
        val matchQuartier = quartierFiltre.isEmpty() ||
                r.adresse.startsWith(quartierFiltre, ignoreCase = true)
        matchNom && matchType && matchQuartier
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClair)
    ) {
        // ── Header ──────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BleuFonce)
                .padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sans),
                contentDescription = "Logo Mizaha",
                modifier = Modifier.width(180.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        // ── Zone de filtres ─────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Recherche nom
            OutlinedTextField(
                value = searchNom,
                onValueChange = { searchNom = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = {
                    Text("Chercher un restaurant…", color = Color.Gray, fontSize = 14.sp)
                },
                leadingIcon = {
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = Orange)
                },
                trailingIcon = {
                    if (searchNom.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Effacer",
                            tint = Orange,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { searchNom = "" }
                        )
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Gris,
                    cursorColor = Orange
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
            )

            // Recherche quartier + suggestions
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = quartierFiltre,
                    onValueChange = {
                        quartierFiltre = it
                        showQuartierSuggestions = it.isNotEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    placeholder = {
                        Text("Quartier…", color = Color.Gray, fontSize = 14.sp)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = BleuCiel
                        )
                    },
                    trailingIcon = {
                        if (quartierFiltre.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Effacer",
                                tint = BleuCiel,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        quartierFiltre = ""
                                        showQuartierSuggestions = false
                                    }
                            )
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = BleuCiel,
                        unfocusedBorderColor = Gris,
                        cursorColor = BleuCiel
                    ),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )

                if (showQuartierSuggestions && quartiersFiltres.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 56.dp)
                            .border(1.dp, Gris, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column {
                            quartiersFiltres.take(5).forEachIndexed { index, quartier ->
                                Text(
                                    text = quartier,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            quartierFiltre = quartier
                                            showQuartierSuggestions = false
                                        }
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    fontSize = 14.sp,
                                    color = BleuFonce
                                )
                                if (index < quartiersFiltres.take(5).lastIndex) {
                                    HorizontalDivider(color = Gris, thickness = 0.5.dp)
                                }
                            }
                        }
                    }
                }
            }

            // Chips de type
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach { type ->
                    val selected = typeFiltre == type
                    FilterChip(
                        selected = selected,
                        onClick = { typeFiltre = type },
                        label = {
                            Text(
                                text = type,
                                fontSize = 13.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Orange,
                            selectedLabelColor = Color.White,
                            containerColor = GrisClair,
                            labelColor = BleuFonce
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            borderColor = if (selected) Orange else Gris,
                            selectedBorderColor = Orange,
                            borderWidth = 1.dp
                        )
                    )
                }
            }
        }

        // ── Compteur ────────────────────────────────────────────────────────
        Text(
            text = "${restaurantsFiltres.size} restaurant${if (restaurantsFiltres.size > 1) "s" else ""}",
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        // ── Liste ───────────────────────────────────────────────────────────
        if (restaurantsFiltres.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("😔", fontSize = 52.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Aucun restaurant trouvé",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BleuFonce
                    )
                    Text(
                        "Essaie une autre recherche",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)
            ) {
                items(restaurantsFiltres, key = { it.id }) { restaurant ->
                    RestaurantCard(restaurant)
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    val (bgColor, iconRes) = when (restaurant.type) {
        "Français" -> Orange to R.drawable.ic_francaise
        "Malgache" -> BleuFonce to R.drawable.ic_malgache
        "Chinois", "Asiatique", "Thaï", "Japonais" -> BleuCiel to R.drawable.ic_chinoise
        "Vietnamien" -> Color(0xFFC74036) to R.drawable.ic_vietnamienne
        "Grill" -> Color(0xFFC74036) to R.drawable.ic_grill
        "Fruits de mer", "Poisson" -> Color(0xFF1A90A0) to R.drawable.ic_fdm
        "Italien" -> Color(0xFFE67E22) to R.drawable.ic_italienne
        else -> BleuCiel to R.drawable.ic_defaut
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Icône type (image PNG)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Infos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = restaurant.nom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = restaurant.type,
                        fontSize = 12.sp,
                        color = Orange,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "  ·  ",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = restaurant.adresse,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                restaurant.telephone?.let { tel ->
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = BleuCiel
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = tel,
                            fontSize = 12.sp,
                            color = BleuCiel,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Bouton détail
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BleuCiel.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Voir détails",
                    tint = BleuCiel,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}