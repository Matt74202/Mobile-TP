package mg.itu.mizaha.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** Un segment de tracé de ligne de bus (une relation OSM peut contenir plusieurs segments/ways). */
data class BusRouteSegment(
    val relationId: Long,
    val nomBrut: String?,
    val nomAffiche: String,
    val points: List<GeoPoint>
)

/**
 * Récupère les lignes de bus (tag OSM route=bus) dans une zone donnée via l'API Overpass.
 * bbox format: "sud,ouest,nord,est"
 */
suspend fun fetchBusRoutes(
    bbox: String = "-18.975,47.470,-18.820,47.580" // zone Antananarivo
): List<BusRouteSegment> = withContext(Dispatchers.IO) {
    val query = """
        [out:json][timeout:60];
        (
          relation["route"="bus"]($bbox);
        );
        out geom;
    """.trimIndent()

    val url = URL("https://overpass-api.de/api/interpreter")
    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "POST"
        doOutput = true
        connectTimeout = 15_000
        readTimeout = 30_000
    }

    connection.outputStream.use { out ->
        out.write("data=${URLEncoder.encode(query, "UTF-8")}".toByteArray())
    }

    val responseText = if (connection.responseCode in 200..299) {
        connection.inputStream.bufferedReader().use { it.readText() }
    } else {
        connection.errorStream?.bufferedReader()?.use { it.readText() }
            ?: throw IllegalStateException("Overpass a répondu ${connection.responseCode}")
    }

    val json = JSONObject(responseText)
    val elements = json.optJSONArray("elements") ?: return@withContext emptyList()

    val segments = mutableListOf<BusRouteSegment>()
    for (i in 0 until elements.length()) {
        val element = elements.getJSONObject(i)
        if (element.optString("type") != "relation") continue

        val relationId = element.optLong("id")
        val tags = element.optJSONObject("tags")
        val nomBrut = tags?.optString("name")?.takeIf { it.isNotBlank() }
        val ref = tags?.optString("ref")?.takeIf { it.isNotBlank() }
        val depuis = tags?.optString("from")?.takeIf { it.isNotBlank() }
        val vers = tags?.optString("to")?.takeIf { it.isNotBlank() }
        val nomAffiche = construireNomAffiche(ref, depuis, vers, nomBrut)

        val members = element.optJSONArray("members") ?: continue
        for (m in 0 until members.length()) {
            val member = members.getJSONObject(m)
            val geometry = member.optJSONArray("geometry") ?: continue

            val points = ArrayList<GeoPoint>(geometry.length())
            for (g in 0 until geometry.length()) {
                val pt = geometry.optJSONObject(g) ?: continue
                if (pt.has("lat") && pt.has("lon")) {
                    points.add(GeoPoint(pt.getDouble("lat"), pt.getDouble("lon")))
                }
            }
            if (points.size >= 2) {
                segments.add(BusRouteSegment(relationId, nomBrut, nomAffiche, points))
            }
        }
    }
    segments
}

/**
 * Construit un nom lisible pour l'utilisateur, en priorisant les tags OSM structurés
 * (ref = numéro/code de ligne, from/to = terminus) plutôt que le tag "name" libre,
 * souvent mal formaté ou incohérent d'une ligne à l'autre.
 */
private fun construireNomAffiche(ref: String?, depuis: String?, vers: String?, nomBrut: String?): String {
    return when {
        ref != null && depuis != null && vers != null -> "Ligne $ref : $depuis ↔ $vers"
        depuis != null && vers != null -> "$depuis ↔ $vers"
        ref != null -> "Ligne $ref"
        else -> nettoyerNomBrut(nomBrut)
    }
}

/** Filet de sécurité si aucun tag structuré n'est disponible : nettoie le tag "name" brut. */
private fun nettoyerNomBrut(nom: String?): String {
    if (nom.isNullOrBlank()) return "Ligne de bus"
    return nom
        .replace('_', ' ')
        .replace('-', ' ')
        .replace(Regex("\\s+"), " ")
        .trim()
        .split(" ")
        .joinToString(" ") { mot ->
            mot.replaceFirstChar { c -> c.uppercaseChar() }
        }
}