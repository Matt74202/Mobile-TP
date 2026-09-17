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
import androidx.compose.ui.draw.blur
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.Restaurant
import androidx.compose.material.icons.outlined.NearMe
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

// Couleurs d'origine — restaurées telles quelles
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
    var plusProches by remember { mutableStateOf(false) }

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
        // ── Header : blanc, logo aligné à gauche ───────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sans),
                contentDescription = "Logo Mizaha",
                modifier = Modifier.width(180.dp),
                contentScale = ContentScale.FillWidth
            )
        }
        HorizontalDivider(color = Gris, thickness = 0.5.dp)

        // ── Zone de filtres ─────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val plusProches = remember { mutableStateOf(false) }
                FilterChip(
                    selected = plusProches.value,
                    onClick = { plusProches.value = !plusProches.value },
                    label = { Text("Plus proches", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.NearMe,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BleuCiel,
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White,
                        containerColor = GrisClair,
                        labelColor = BleuFonce,
                        iconColor = BleuFonce
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = plusProches.value,
                        borderColor = if (plusProches.value) BleuCiel else Gris,
                        selectedBorderColor = BleuCiel,
                        borderWidth = 1.dp
                    )
                )

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

        Text(
            text = "${restaurantsFiltres.size} restaurant${if (restaurantsFiltres.size > 1) "s" else ""}",
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        if (restaurantsFiltres.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.er_resto),
                        contentDescription = "Aucun résultat",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Aucun restaurant trouvé",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BleuFonce
                    )
                    Text(
                        "Essayez une autre recherche",
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
    val context = LocalContext.current
    var showCallDialog by remember { mutableStateOf(false) }

    val (bgColor, iconRes) = when (restaurant.type) {
        "Français" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_francaise
        "Malgache" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_malgache
        "Chinois", "Asiatique", "Thaï", "Japonais" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_chinoise
        "Vietnamien" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_vietnamienne
        "Grill" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_grill
        "Fruits de mer", "Poisson" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_fdm
        "Italien" -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_italienne
        else -> BleuCiel.copy(alpha = 0.15f) to R.drawable.ic_defaut
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .border(1.dp, Gris, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE0E0E0).copy(alpha = 0.35f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val query = "${restaurant.nom}, ${restaurant.adresse}, Antananarivo"
                        val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }
                    }
                ) {
                    Text(
                        text = restaurant.type,
                        fontSize = 12.sp,
                        color = Color.Gray,
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
                        color = BleuCiel,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                restaurant.telephone?.let { tel ->
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showCallDialog = true }
                    ) {
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

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BleuFonce),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Voir détails",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    if (showCallDialog && restaurant.telephone != null) {
        AlertDialog(
            onDismissRequest = { showCallDialog = false },
            title = { Text("Appeler ?") },
            text = { Text("Voulez-vous appeler ${restaurant.nom} au ${restaurant.telephone} ?") },
            confirmButton = {
                TextButton(onClick = {
                    showCallDialog = false
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${restaurant.telephone}"))
                    context.startActivity(intent)
                }) {
                    Text("Appeler", color = BleuFonce, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCallDialog = false }) {
                    Text("Annuler", color = Color.Gray)
                }
            }
        )
    }
}