package mg.itu.mizaha.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mg.itu.mizaha.R
import mg.itu.mizaha.data.entities.ContactUrgence
import androidx.compose.foundation.Image

private val Rouge = Color(0xFFD32F2F)
private val RougeFonce = Color(0xFFB71C1C)
private val BleuFonce = Color(0xFF1E243A)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)

@Composable
fun UrgenceScreen(
    contacts: List<ContactUrgence>,
    contactPlusProche: ContactUrgence
) {
    var quartierFiltre by remember { mutableStateOf("") }
    var showQuartierSuggestions by remember { mutableStateOf(false) }
    var showCallDialog by remember { mutableStateOf<ContactUrgence?>(null) }
    val context = LocalContext.current

    val quartiers = contacts.map { it.quartier }.distinct().sorted()
    val quartiersFiltres = if (quartierFiltre.isNotEmpty()) {
        quartiers.filter { it.startsWith(quartierFiltre, ignoreCase = true) }
    } else quartiers

    val contactsFiltres = contacts.filter { c ->
        quartierFiltre.isEmpty() || c.quartier.startsWith(quartierFiltre, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClair)
    ) {
        // ── Header rouge ────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(RougeFonce)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Urgence",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Police & Gendarmerie",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ── Card "le plus proche" ────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { showCallDialog = contactPlusProche },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Rouge)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(
                                    id = if (contactPlusProche.type.equals("Gendarmerie", ignoreCase = true)
                                        || contactPlusProche.type.equals("Gendarme", ignoreCase = true)
                                    ) R.drawable.ic_ur_gendarme
                                    else R.drawable.ic_ur_police
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Poste le plus proche",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = contactPlusProche.nom,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = contactPlusProche.telephone,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "Appeler",
                                tint = RougeFonce,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            // ── Filtre par quartier ───────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Tous les postes",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BleuFonce,
                        modifier = Modifier.padding(bottom = 10.dp)
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
                                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Rouge)
                            },
                            trailingIcon = {
                                if (quartierFiltre.isNotEmpty()) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = "Effacer",
                                        tint = Rouge,
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
                                focusedBorderColor = Rouge,
                                unfocusedBorderColor = Gris,
                                cursorColor = Rouge
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
                }
            }

            item {
                Text(
                    text = "${contactsFiltres.size} poste${if (contactsFiltres.size > 1) "s" else ""}",
                    modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 8.dp),
                    fontSize = 13.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            items(contactsFiltres, key = { it.id }) { contact ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    ContactUrgenceCard(contact) { showCallDialog = contact }
                }
            }
        }
    }

    showCallDialog?.let { contact ->
        AlertDialog(
            onDismissRequest = { showCallDialog = null },
            title = { Text("Appeler ?") },
            text = { Text("Voulez-vous appeler ${contact.nom} au ${contact.telephone} ?") },
            confirmButton = {
                TextButton(onClick = {
                    showCallDialog = null
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.telephone}"))
                    context.startActivity(intent)
                }) {
                    Text("Appeler", color = RougeFonce, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCallDialog = null }) {
                    Text("Annuler", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun ContactUrgenceCard(contact: ContactUrgence, onCallClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCallClick() }
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
                    .size(48.dp)
                    .background(Rouge.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (contact.type.equals("Gendarmerie", ignoreCase = true)
                            || contact.type.equals("Gendarme", ignoreCase = true)
                        ) R.drawable.ic_ur_gendarme
                        else R.drawable.ic_ur_police
                    ),
                    contentDescription = contact.type,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.nom,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = contact.type,
                        fontSize = 12.sp,
                        color = Rouge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = "  ·  ", fontSize = 12.sp, color = Color.LightGray)
                    Text(text = contact.quartier, fontSize = 12.sp, color = Color.Gray)
                }
                Text(
                    text = contact.telephone,
                    fontSize = 13.sp,
                    color = Rouge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Rouge, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Appeler",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}