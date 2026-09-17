package mg.itu.mizaha.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.LocationOn
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
import mg.itu.mizaha.data.entities.Pharmacie
import mg.itu.mizaha.data.entities.*
import androidx.compose.material.icons.outlined.NearMe

private val BleuFonce = Color(0xFF1E243A)
private val BleuCiel = Color(0xFF51A5C7)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)
private val Vert = Color(0xFF87A554)

@Composable
fun PharmaciesScreen(
    pharmacies: List<Pharmacie>,
    semaineDeGarde: SemaineDeGarde
) {
    var lieuFiltre by remember { mutableStateOf("") }
    var showLieuSuggestions by remember { mutableStateOf(false) }
    var plusProches by remember { mutableStateOf(false) }   // ← ajouté

    val lieux = pharmacies.map { it.lieu }.distinct().sorted()
    val lieuxFiltres = if (lieuFiltre.isNotEmpty()) {
        lieux.filter { it.startsWith(lieuFiltre, ignoreCase = true) }
    } else lieux

    val pharmaciesFiltrees = pharmacies.filter { p ->
        lieuFiltre.isEmpty() || p.lieu.startsWith(lieuFiltre, ignoreCase = true)
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ── Card pharmacies de garde ────────────────────────────────────
            item {
                PharmaciesDeGardeCard(semaineDeGarde)
            }

            // ── Zone de filtre lieu ──────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = lieuFiltre,
                            onValueChange = {
                                lieuFiltre = it
                                showLieuSuggestions = it.isNotEmpty()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            placeholder = {
                                Text("Quartier…", color = Color.Gray, fontSize = 14.sp)
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = BleuCiel)
                            },
                            trailingIcon = {
                                if (lieuFiltre.isNotEmpty()) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = "Effacer",
                                        tint = BleuCiel,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                lieuFiltre = ""
                                                showLieuSuggestions = false
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

                        if (showLieuSuggestions && lieuxFiltres.isNotEmpty()) {
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
                                    lieuxFiltres.take(5).forEachIndexed { index, lieu ->
                                        Text(
                                            text = lieu,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    lieuFiltre = lieu
                                                    showLieuSuggestions = false
                                                }
                                                .padding(horizontal = 16.dp, vertical = 14.dp),
                                            fontSize = 14.sp,
                                            color = BleuFonce
                                        )
                                        if (index < lieuxFiltres.take(5).lastIndex) {
                                            HorizontalDivider(color = Gris, thickness = 0.5.dp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // ── Chip "Plus proches" ──────────────────────────────────────────
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
                }
            }

            item {
                Text(
                    text = "${pharmaciesFiltrees.size} pharmacie${if (pharmaciesFiltrees.size > 1) "s" else ""}",
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp, bottom = 8.dp),
                    fontSize = 13.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            items(pharmaciesFiltrees, key = { it.id }) { pharmacie ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    PharmacieCard(pharmacie)
                }
            }
        }
    }
}

@Composable
private fun PharmaciesDeGardeCard(semaine: SemaineDeGarde) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor =Vert)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pharmacies de garde",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Du ${semaine.dateDebut} au ${semaine.dateFin}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Réduire" else "Développer",
                    tint = Color.White
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    semaine.pharmacies.forEach { garde ->
                        Text(
                            text = "${garde.nom} — ${garde.lieu}",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PharmacieCard(pharmacie: Pharmacie) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val query = "${pharmacie.nom}, ${pharmacie.lieu}, Antananarivo"
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
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
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pharmacie.nom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Vert
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = pharmacie.lieu, fontSize = 13.sp, color = Vert)
                }
            }
        }
    }
}