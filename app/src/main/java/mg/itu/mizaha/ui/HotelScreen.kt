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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.Hotel
import androidx.compose.material.icons.outlined.LocationOn
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip

private val BleuFonce = Color(0xFF1E243A)
private val BleuCiel = Color(0xFF51A5C7)
private val Orange = Color(0xFFE8602D)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)

@Composable
fun HotelsScreen(hotels: List<Hotel>) {
    var searchNom by remember { mutableStateOf("") }
    var quartierFiltre by remember { mutableStateOf("") }
    var showQuartierSuggestions by remember { mutableStateOf(false) }
    var plusProches by remember { mutableStateOf(false) }

    val quartiers = hotels.map { it.lieu }.distinct().sorted()
    val quartiersFiltres = if (quartierFiltre.isNotEmpty()) {
        quartiers.filter { it.startsWith(quartierFiltre, ignoreCase = true) }
    } else quartiers

    val hotelsFiltres = hotels.filter { h ->
        val matchNom = h.nom.contains(searchNom, ignoreCase = true)
        val matchQuartier = quartierFiltre.isEmpty() ||
                h.lieu.startsWith(quartierFiltre, ignoreCase = true)
        matchNom && matchQuartier
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
            // Recherche nom
            OutlinedTextField(
                value = searchNom,
                onValueChange = { searchNom = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = {
                    Text("Chercher un hôtel…", color = Color.Gray, fontSize = 14.sp)
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
                        Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = BleuCiel)
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

            // Chip "Plus proches"
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

        // ── Compteur ────────────────────────────────────────────────────────
        Text(
            text = "${hotelsFiltres.size} hôtel${if (hotelsFiltres.size > 1) "s" else ""}",
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        // ── Liste ou état vide ───────────────────────────────────────────────
        if (hotelsFiltres.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),                 // ← maintenant c’est correct
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.er_hotel),
                        contentDescription = "Aucun résultat",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Aucun hôtel trouvé",
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
                items(hotelsFiltres, key = { it.id }) { hotel ->
                    HotelCard(hotel)
                }
            }
        }
    }
}

@Composable
fun HotelCard(hotel: Hotel) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var showCallDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gris, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE0E0E0).copy(alpha = 0.35f)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = hotel.imageRes),
                contentDescription = hotel.nom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = hotel.nom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val query = "${hotel.nom}, ${hotel.lieu}, Antananarivo"
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
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = BleuCiel
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = hotel.lieu, fontSize = 13.sp, color = BleuCiel)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { showCallDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = BleuCiel
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = hotel.contact,
                        fontSize = 13.sp,
                        color = BleuCiel,
                        fontWeight = FontWeight.Medium
                    )
                }

                hotel.siteWeb?.let { url ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { uriHandler.openUri(url) }
                            .padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = BleuFonce
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Visiter le site web",
                            fontSize = 13.sp,
                            color = BleuFonce,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    if (showCallDialog) {
        AlertDialog(
            onDismissRequest = { showCallDialog = false },
            title = { Text("Appeler ?") },
            text = { Text("Voulez-vous appeler ${hotel.nom} au ${hotel.contact} ?") },
            confirmButton = {
                TextButton(onClick = {
                    showCallDialog = false
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${hotel.contact}"))
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