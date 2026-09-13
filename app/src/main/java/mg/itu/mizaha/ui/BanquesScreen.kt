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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.Banque

private val BleuFonce = Color(0xFF1E243A)
private val BleuCiel = Color(0xFF51A5C7)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)
private val Orange = Color(0xFFE8602D)

@Composable
fun BanquesScreen(banques: List<Banque>) {
    var enseigneFiltre by remember { mutableStateOf("Toutes") }
    var lieuFiltre by remember { mutableStateOf("") }
    var showLieuSuggestions by remember { mutableStateOf(false) }
    var plusProches by remember { mutableStateOf(false) }

    val enseignes = listOf("Toutes") + banques.map { it.enseigne }.distinct().sorted()
    val lieux = banques.map { it.lieu }.distinct().sorted()
    val lieuxFiltres = if (lieuFiltre.isNotEmpty()) {
        lieux.filter { it.startsWith(lieuFiltre, ignoreCase = true) }
    } else lieux

    val banquesFiltrees = banques.filter { b ->
        val matchEnseigne = enseigneFiltre == "Toutes" || b.enseigne == enseigneFiltre
        val matchLieu = lieuFiltre.isEmpty() || b.lieu.startsWith(lieuFiltre, ignoreCase = true)
        matchEnseigne && matchLieu
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
            // Recherche lieu + suggestions
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

            // Chips : "Plus proches" en premier, puis enseignes
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
                        selectedContainerColor = Orange,
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

                enseignes.forEach { enseigne ->
                    val selected = enseigneFiltre == enseigne
                    FilterChip(
                        selected = selected,
                        onClick = { enseigneFiltre = enseigne },
                        label = { Text(enseigne, fontSize = 13.sp) },
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
            text = "${banquesFiltrees.size} agence${if (banquesFiltrees.size > 1) "s" else ""}",
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)
        ) {
            items(banquesFiltrees, key = { it.id }) { banque ->
                BanqueCard(banque)
            }
        }
    }
}

@Composable
fun BanqueCard(banque: Banque) {
    val context = LocalContext.current

    val logoRes = when (banque.enseigne) {
        "BNI" -> R.drawable.bnq_logo_bni
        "BOA" -> R.drawable.bnq_logo_boa
        "BFV-SG" -> R.drawable.bnq_logo_bfv
        "BMOI" -> R.drawable.bnq_logo_bmoi
        "Access Bank" -> R.drawable.bnq_logo_access
        "AFG Bank" -> R.drawable.bnq_logo_afg
        else -> R.drawable.bnq_logo_bni
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
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
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = logoRes),
                    contentDescription = banque.enseigne,
                    modifier = Modifier.size(40.dp),
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
                    text = banque.nom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {          // ← ajouté
                        val query = "${banque.nom}, ${banque.lieu}, Antananarivo"
                        val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Google Maps non installé → ouvre sans forcer le package
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp),
                        tint = BleuCiel
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = banque.lieu,
                        fontSize = 12.sp,
                        color = BleuCiel,          // ← changé de Gray à BleuCiel pour signaler que c'est cliquable
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}