package mg.itu.mizaha.data.repository

import kotlinx.coroutines.flow.first
import mg.itu.mizaha.data.dao.*
import javax.inject.Inject
import kotlin.math.*

data class Suggestion(val nom: String, val categorie: String, val distanceMetres: Double, val score: Double)

class SuggestionRepository @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val hotelDao: HotelDao,
    private val banqueDao: BanqueDao,
    private val pharmacieDao: PharmacieDao,
    private val prefDao: UserPreferenceDao
) {
    suspend fun onNewLocation(lat: Double, lng: Double): List<Suggestion> {
        val restaurants = restaurantDao.getAll().first()
        val prefs = prefDao.getAll().first().associate { it.categorie to it.score }

        return restaurants
            .map { r ->
                val distance = haversine(lat, lng, r.latitude, r.longitude)
                val prefScore = prefs[r.type] ?: 0f
                Suggestion(
                    nom = r.nom,
                    categorie = r.type,
                    distanceMetres = distance,
                    score = scoreCombine(distance, prefScore)
                )
            }
            .filter { it.distanceMetres < RAYON_MAX_METRES }
            .sortedByDescending { it.score }
            .take(10)
    }

    private fun scoreCombine(distanceMetres: Double, prefScore: Float): Double {
        val proximite = 1.0 / (1.0 + distanceMetres / 500.0)
        val poidsPreference = 1.0 + prefScore * 0.3
        return proximite * poidsPreference
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        return r * 2 * atan2(sqrt(a), sqrt(1 - a))
    }

    companion object { const val RAYON_MAX_METRES = 3000.0 }
}
