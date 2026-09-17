package mg.itu.mizaha.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import mg.itu.mizaha.data.network.BusRouteSegment
import mg.itu.mizaha.data.network.fetchBusRoutes
import mg.itu.mizaha.data.network.geocode
import mg.itu.mizaha.util.distanceMetres
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

private val BleuFonce = Color(0xFF1E243A)
private val BleuCiel = Color(0xFF51A5C7)
private val Orange = Color(0xFFE8602D)
private val Gris = Color(0xFFE7E6E8)
private val GrisClair = Color(0xFFF8F8F9)

private const val RAYON_ARRET_METRES = 400.0

private val couleursLignes = listOf(
    android.graphics.Color.parseColor("#E8602D"),
    android.graphics.Color.parseColor("#1E90C7"),
    android.graphics.Color.parseColor("#1E243A"),
    android.graphics.Color.parseColor("#2E8B57"),
    android.graphics.Color.parseColor("#8E44AD"),
    android.graphics.Color.parseColor("#C0392B")
)
private val couleurSurbrillance = android.graphics.Color.parseColor("#E8602D")
private val couleurAttenuee = android.graphics.Color.parseColor("#DDDDDD")

@Composable
fun MapScreen(
    userLat: Double? = null,
    userLng: Double? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    var arriveeTexte by remember { mutableStateOf("") }
    var rechercheLigneTexte by remember { mutableStateOf("") }
    var showSuggestionsLigne by remember { mutableStateOf(false) }
    var pointDepart by remember {
        mutableStateOf(
            if (userLat != null && userLng != null) GeoPoint(userLat, userLng) else null
        )
    }

    var toutesLesLignes by remember { mutableStateOf<List<BusRouteSegment>>(emptyList()) }
    var lignesTrouvees by remember { mutableStateOf<List<BusRouteSegment>>(emptyList()) }
    var rechercheEnCours by remember { mutableStateOf(false) }
    var chargementInitial by remember { mutableStateOf(true) }
    var localisationEnCours by remember { mutableStateOf(true) }
    var messageErreur by remember { mutableStateOf<String?>(null) }

    val mapView = remember {
        MapView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(13.5)
            controller.setCenter(pointDepart ?: GeoPoint(-18.8792, 47.5079))
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDetach()
        }
    }

    // Localisation de l'utilisateur
    LaunchedEffect(Unit) {
        val permissionAccordee = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionAccordee) {
            localisationEnCours = false
            messageErreur = "Active la localisation pour utiliser le point de départ automatique"
            return@LaunchedEffect
        }

        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
        locationOverlay.enableMyLocation()

        locationOverlay.runOnFirstFix {
            val loc = locationOverlay.myLocation
            Handler(Looper.getMainLooper()).post {
                localisationEnCours = false
                if (loc != null) {
                    pointDepart = loc
                    mapView.controller.setCenter(loc)
                }
            }
        }

        mapView.overlays.add(locationOverlay)
        mapView.invalidate()
    }

    // Chargement initial de toutes les lignes de bus
    LaunchedEffect(Unit) {
        chargementInitial = true
        try {
            toutesLesLignes = fetchBusRoutes()
        } catch (e: Exception) {
            messageErreur = "Impossible de charger les lignes de bus"
        } finally {
            chargementInitial = false
        }
    }

    // Redessine les lignes à chaque changement
    LaunchedEffect(toutesLesLignes, lignesTrouvees) {
        mapView.overlays.removeAll { it is Polyline }
        val idsTrouves = lignesTrouvees.map { it.relationId }.toSet()

        toutesLesLignes.forEachIndexed { index, segment ->
            val estSurligne = idsTrouves.isEmpty() || segment.relationId in idsTrouves
            val polyline = Polyline(mapView).apply {
                setPoints(segment.points)
                outlinePaint.color = when {
                    idsTrouves.isEmpty() -> couleursLignes[index % couleursLignes.size]
                    estSurligne -> couleurSurbrillance
                    else -> couleurAttenuee
                }
                outlinePaint.strokeWidth = if (estSurligne) 8f else 3f
                title = segment.nomAffiche
                setOnClickListener { _, _, _ ->
                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                    true
                }
            }
            mapView.overlays.add(polyline)
        }
        mapView.invalidate()
    }

    fun rechercherTrajet() {
        val depart = pointDepart
        if (depart == null) {
            messageErreur = "Position de départ inconnue — active la localisation"
            return
        }
        if (arriveeTexte.isBlank()) {
            messageErreur = "Indique une destination"
            return
        }

        scope.launch {
            rechercheEnCours = true
            messageErreur = null
            try {
                val arrivee = geocode(arriveeTexte)
                if (arrivee == null) {
                    messageErreur = "Destination introuvable"
                    lignesTrouvees = emptyList()
                    return@launch
                }

                val correspondances = toutesLesLignes
                    .groupBy { it.relationId }
                    .filter { (_, segments) ->
                        val points = segments.flatMap { it.points }
                        points.any {
                            distanceMetres(it.latitude, it.longitude, depart.latitude, depart.longitude) < RAYON_ARRET_METRES
                        } && points.any {
                            distanceMetres(it.latitude, it.longitude, arrivee.latitude, arrivee.longitude) < RAYON_ARRET_METRES
                        }
                    }
                    .flatMap { it.value }

                lignesTrouvees = correspondances

                if (correspondances.isEmpty()) {
                    messageErreur = "Aucune ligne directe trouvée entre ces deux points"
                } else {
                    val tousPoints = correspondances.flatMap { it.points } + listOf(depart, arrivee)
                    mapView.zoomToBoundingBox(BoundingBox.fromGeoPoints(tousPoints), true, 100)
                }
            } catch (e: Exception) {
                messageErreur = "Erreur lors de la recherche : ${e.message}"
            } finally {
                rechercheEnCours = false
            }
        }
    }

    fun rechercherParNumeroLigne() {
        if (rechercheLigneTexte.isBlank()) {
            messageErreur = "Indique un nom ou numéro de ligne"
            return
        }

        val correspondances = toutesLesLignes.filter { segment ->
            segment.nomAffiche.contains(rechercheLigneTexte, ignoreCase = true)
        }

        lignesTrouvees = correspondances
        messageErreur = null

        if (correspondances.isEmpty()) {
            messageErreur = "Aucune ligne ne correspond à « $rechercheLigneTexte »"
        } else {
            val tousPoints = correspondances.flatMap { it.points }
            mapView.zoomToBoundingBox(BoundingBox.fromGeoPoints(tousPoints), true, 100)
        }
    }

    fun reinitialiserRecherches() {
        lignesTrouvees = emptyList()
        arriveeTexte = ""
        rechercheLigneTexte = ""
        showSuggestionsLigne = false
        messageErreur = null
    }

    // Liste dédupliquée des noms affichables, pour l'autocomplete
    val suggestionsLignes = remember(toutesLesLignes, rechercheLigneTexte) {
        if (rechercheLigneTexte.isBlank()) emptyList()
        else toutesLesLignes
            .distinctBy { it.relationId }
            .map { it.nomAffiche }
            .distinct()
            .filter { it.contains(rechercheLigneTexte, ignoreCase = true) }
            .take(6)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ── Carte en plein écran (arrière-plan) ─────────────────────────────
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { mapView })

        // ── Bloc de filtres flottant par-dessus, ancré en haut ──────────────
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .heightIn(max = 420.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "Rechercher un trajet",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BleuFonce
                )

                // Champ Départ (lecture seule = position actuelle)
                OutlinedTextField(
                    value = when {
                        localisationEnCours -> "Localisation en cours…"
                        pointDepart != null -> "Ma position actuelle"
                        else -> "Position indisponible"
                    },
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    leadingIcon = {
                        if (localisationEnCours) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = BleuCiel
                            )
                        } else {
                            Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = BleuCiel)
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = GrisClair,
                        disabledBorderColor = Gris,
                        disabledTextColor = BleuFonce,
                        disabledLeadingIconColor = BleuCiel
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                // Champ Arrivée
                OutlinedTextField(
                    value = arriveeTexte,
                    onValueChange = { arriveeTexte = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    placeholder = { Text("Aller à…", color = Color.Gray, fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(Icons.Rounded.Search, contentDescription = null, tint = Orange)
                    },
                    trailingIcon = {
                        if (arriveeTexte.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Effacer",
                                tint = Orange,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        arriveeTexte = ""
                                        lignesTrouvees = emptyList()
                                        messageErreur = null
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
                    textStyle = TextStyle(fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { rechercherTrajet() })
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { rechercherTrajet() },
                        enabled = !rechercheEnCours,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        modifier = Modifier.height(44.dp)
                    ) {
                        if (rechercheEnCours) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Voir le trajet", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    if (lignesTrouvees.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { reinitialiserRecherches() },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Text("Réinitialiser", fontSize = 13.sp, color = BleuFonce)
                        }
                    }
                }

                // ── Séparateur "OU" ─────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Gris)
                    Text(
                        "  OU  ",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Gris)
                }

                // ── Recherche directe d'une ligne de bus ─────────────────────
                Text(
                    "Chercher une ligne précise",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BleuFonce
                )

                OutlinedTextField(
                    value = rechercheLigneTexte,
                    onValueChange = {
                        rechercheLigneTexte = it
                        showSuggestionsLigne = it.isNotEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    placeholder = {
                        Text("Ex: Ligne 12, Analakely…", color = Color.Gray, fontSize = 14.sp)
                    },
                    leadingIcon = {
                        Icon(Icons.Rounded.Search, contentDescription = null, tint = BleuCiel)
                    },
                    trailingIcon = {
                        if (rechercheLigneTexte.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Effacer",
                                tint = BleuCiel,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        rechercheLigneTexte = ""
                                        showSuggestionsLigne = false
                                        lignesTrouvees = emptyList()
                                        messageErreur = null
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
                    textStyle = TextStyle(fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        showSuggestionsLigne = false
                        rechercherParNumeroLigne()
                    })
                )

                if (showSuggestionsLigne && suggestionsLignes.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Gris, RoundedCornerShape(12.dp))
                    ) {
                        suggestionsLignes.forEachIndexed { index, suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        rechercheLigneTexte = suggestion
                                        showSuggestionsLigne = false
                                        rechercherParNumeroLigne()
                                    }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                fontSize = 13.sp,
                                color = BleuFonce
                            )
                            if (index < suggestionsLignes.lastIndex) {
                                HorizontalDivider(color = Gris, thickness = 0.5.dp)
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { rechercherParNumeroLigne() },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BleuCiel),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text("Afficher cette ligne", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                    if (lignesTrouvees.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { reinitialiserRecherches() },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Text("Réinitialiser", fontSize = 13.sp, color = BleuFonce)
                        }
                    }
                }

                messageErreur?.let {
                    Text(it, color = Color(0xFFC0392B), fontSize = 12.sp)
                }
            }
        }

        // ── Indicateur de chargement initial des lignes de bus ──────────────
        if (chargementInitial) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Orange
                    )
                    Text("Chargement des lignes de bus…", fontSize = 13.sp, color = BleuFonce)
                }
            }
        }
    }
}