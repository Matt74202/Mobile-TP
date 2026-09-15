package mg.itu.mizaha.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.Activite
import androidx.compose.ui.draw.clip

private val BleuFonce = Color(0xFF1E243A)
private val BleuCiel = Color(0xFF51A5C7)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)
private val Orange = Color(0xFFE8602D)

@Composable
fun ActivitesScreen(activites: List<Activite>) {
    var searchActivite by remember { mutableStateOf("") }
    var typeFiltre by remember { mutableStateOf("Tous") }
    var quartierFiltre by remember { mutableStateOf("") }
    var showQuartierSuggestions by remember { mutableStateOf(false) }
    var plusProches by remember { mutableStateOf(false) }

    val types = listOf("Tous") + activites.map { it.type }.distinct().sorted()
    val quartiers = activites.map { it.quartier }.distinct().sorted()
    val quartiersFiltres = if (quartierFiltre.isNotEmpty()) {
        quartiers.filter { it.startsWith(quartierFiltre, ignoreCase = true) }
    } else quartiers

    val activitesFiltrees = activites.filter { a ->
        val matchNom = a.nomActivite.contains(searchActivite, ignoreCase = true) ||
                a.nomLieu.contains(searchActivite, ignoreCase = true)
        val matchType = typeFiltre == "Tous" || a.type == typeFiltre
        val matchQuartier = quartierFiltre.isEmpty() ||
                a.quartier.startsWith(quartierFiltre, ignoreCase = true)
        matchNom && matchType && matchQuartier
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClair)
    ) {
        // ── Header ──────────────────────────────────────────────────────────
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
                modifier = Modifier.width(120.dp),
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Recherche par nom d'activité / lieu
            OutlinedTextField(
                value = searchActivite,
                onValueChange = { searchActivite = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = {
                    Text("Chercher une activité…", color = Color.Gray, fontSize = 14.sp)
                },
                leadingIcon = {
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = BleuCiel)
                },
                trailingIcon = {
                    if (searchActivite.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Effacer",
                            tint = BleuCiel,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { searchActivite = "" }
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
                        Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Orange)
                    },
                    trailingIcon = {
                        if (quartierFiltre.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Effacer",
                                tint = Orange,
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
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Gris,
                        cursorColor = Orange
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

            // Chips : "Plus proches" en premier, puis types
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = plusProches,
                    onClick = { plusProches = !plusProches },
                    label = { Text("Plus proches", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Outlined.NearMe, contentDescription = null, modifier = Modifier.size(16.dp))
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
                        selected = plusProches,
                        borderColor = if (plusProches) BleuCiel else Gris,
                        selectedBorderColor = BleuCiel,
                        borderWidth = 1.dp
                    )
                )

                types.forEach { type ->
                    val selected = typeFiltre == type
                    FilterChip(
                        selected = selected,
                        onClick = { typeFiltre = type },
                        label = { Text(type, fontSize = 13.sp) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BleuFonce,
                            selectedLabelColor = Color.White,
                            containerColor = GrisClair,
                            labelColor = BleuFonce
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            borderColor = if (selected) BleuFonce else Gris,
                            selectedBorderColor = BleuFonce,
                            borderWidth = 1.dp
                        )
                    )
                }
            }
        }

        Text(
            text = "${activitesFiltrees.size} activité${if (activitesFiltrees.size > 1) "s" else ""}",
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        // ── Liste / état vide ───────────────────────────────────────────────
        if (activitesFiltrees.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.er_activite),
                        contentDescription = "Aucun résultat",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Aucune activité trouvée",
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
                items(activitesFiltrees, key = { it.id }) { activite ->
                    ActiviteCard(activite)
                }
            }
        }
    }
}

@Composable
fun ActiviteCard(activite: Activite) {
    val context = LocalContext.current

    val iconRes = when (activite.type) {
        "Sport" -> R.drawable.ic_ac_sport
        "Bien-être" -> R.drawable.ic_ac_bienetre
        "Culture" -> R.drawable.ic_ac_culture
        "Nature" -> R.drawable.ic_ac_nature
        else -> R.drawable.ic_ac_defaut
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                val uri = Uri.parse(
                    "geo:${activite.latitude},${activite.longitude}?q=${activite.latitude},${activite.longitude}(${Uri.encode(activite.nomLieu)})"
                )
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
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
                    .background(BleuCiel.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = activite.type,
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
                    text = activite.nomActivite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = activite.nomLieu,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = activite.type,
                        fontSize = 12.sp,
                        color = BleuFonce,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = "  ·  ", fontSize = 12.sp, color = Color.LightGray)
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp),
                        tint = BleuCiel
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text = activite.quartier,
                        fontSize = 12.sp,
                        color = BleuCiel,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}