package mg.itu.mizaha.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Géocodage via Nominatim (OpenStreetMap) : transforme un texte de lieu en coordonnées GPS.
 * Recherche restreinte à la zone d'Antananarivo via viewbox+bounded pour de meilleurs résultats.
 * ⚠️ Respecte la politique d'usage Nominatim : max 1 requête/seconde, User-Agent obligatoire.
 * Remplace le User-Agent ci-dessous par une valeur qui identifie vraiment ton app/contact.
 */
suspend fun geocode(texte: String): GeoPoint? = withContext(Dispatchers.IO) {
    if (texte.isBlank()) return@withContext null

    val requete = "$texte, Antananarivo, Madagascar"
    val url = URL(
        "https://nominatim.openstreetmap.org/search" +
                "?q=${URLEncoder.encode(requete, "UTF-8")}" +
                "&format=json&limit=1" +
                "&viewbox=47.45,-18.80,47.60,-18.98&bounded=1"
    )

    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        setRequestProperty("User-Agent", "MizahaApp/1.0 (contact@mizaha.mg)")
        connectTimeout = 10_000
        readTimeout = 15_000
    }

    val texteReponse = connection.inputStream.bufferedReader().use { it.readText() }
    val tableau = JSONArray(texteReponse)
    if (tableau.length() == 0) return@withContext null

    val resultat = tableau.getJSONObject(0)
    GeoPoint(resultat.getDouble("lat"), resultat.getDouble("lon"))
}