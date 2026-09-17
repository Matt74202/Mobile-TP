package mg.itu.mizaha.data

import mg.itu.mizaha.data.entities.Restaurant
import mg.itu.mizaha.data.entities.*
import mg.itu.mizaha.R
//------------------------------------------RESTO---------------------------------------------
val restaurantsTest = listOf(
    Restaurant(1, "La Petite Brasserie", "Ambodirapiata", -18.910766, 47.522062, "Français", "+261 34 52 082 01", 4.5),
    Restaurant(2, "La Plantation", "Haute-Ville", -18.879715, 47.522711, "Gastronomie", "+261 34 13 416 80", 4.8),
    Restaurant(3, "L'Arrivage", "Ambohibahy", -18.906911, 47.528008, "Fruits de mer", "+261 32 05 728 24", 4.6),
    Restaurant(8, "Hirondelle Chinese", "Ambohibahy", -18.917414, 47.536628, "Chinois", "+261 34 11 166 78", 4.2),
    Restaurant(18, "BBQ Master", "Ambodirapiata", -18.912000, 47.523000, "Grill", "+261 34 10 000 08", 4.6),
    Restaurant(24, "Côté Mer", "Waterfront", -18.890000, 47.530000, "Fruits de mer", "+261 34 10 000 14", 4.5),
    Restaurant(39, "Steakhouse Prime", "Haute-Ville", -18.872000, 47.517000, "Grill", "+261 34 10 000 29", 4.6)
)

//-------------------------------------------HOTEL----------------------------------------------
val hotelsTest = listOf(
    Hotel(1, "Carlton Madagascar", "Anosy", "+261 20 22 260 60", "https://www.carlton-madagascar.com", R.drawable.hotel_carlton),
    Hotel(2, "Radisson Blu Antananarivo Waterfront", "Waterfront", "+261 20 23 300 00", "https://www.radissonhotels.com", R.drawable.hotel_radisson),
    Hotel(3, "Hôtel Colbert", "Antaninarenina", "+261 20 22 202 02", "https://www.hotel-colbert.mg", R.drawable.hotel_colbert),
    Hotel(4, "Le Louvre Hôtel & Spa", "Antaninarenina", "+261 20 22 341 33", "https://www.lelouvre-hotel.com", R.drawable.hotel_louvre),
    Hotel(5, "Sakamanga Hôtel", "Isoraka", "+261 20 22 358 09", null, R.drawable.hotel_sakamanga),
    Hotel(6, "Hôtel Panorama", "Ankorondrano", "+261 20 22 630 06", "https://www.hotelpanorama.mg", R.drawable.hotel_panorama)
)
//-------------------------------------------BANQUE----------------------------------------------
val banquesTest = listOf(
    Banque(1, "BNI Analakely", "BNI", "Analakely"),
    Banque(2, "BNI Ankorondrano", "BNI", "Ankorondrano"),
    Banque(3, "BOA Antaninarenina", "BOA", "Antaninarenina"),
    Banque(4, "BOA Ankorondrano", "BOA", "Ankorondrano"),
    Banque(5, "BFV-SG Analakely", "BFV-SG", "Analakely"),
    Banque(6, "BMOI Haute-Ville", "BMOI", "Haute-Ville"),
    Banque(7, "Access Bank Ambatonakanga", "Access Bank", "Ambatonakanga"),
    Banque(8, "AFG Bank Tanjombato", "AFG Bank", "Tanjombato")
)
//-------------------------------------------PHARMACIE----------------------------------------------
val pharmaciesTest = listOf(
    Pharmacie(1, "Pharmacie Analakely", "Analakely"),
    Pharmacie(2, "Pharmacie de Ambohijatovo", "Ambohijatovo"),
    Pharmacie(3, "Pharmacie Antaninarenina", "Antaninarenina"),
    Pharmacie(4, "Pharmacie Mahamasina", "Mahamasina"),
    Pharmacie(5, "Pharmacie Isoraka", "Isoraka"),
    Pharmacie(6, "Pharmacie CAPITALE", "Ankazomanga")
)

val semaineDeGardeTest = SemaineDeGarde(
    dateDebut = "12/09/2026",
    dateFin = "19/09/2026",
    pharmacies = listOf(
        PharmacieDeGarde("Pharmacie CAPITALE", "Ankazomanga"),
        PharmacieDeGarde("Pharmacie Isoraka", "Isoraka"),
        PharmacieDeGarde("Pharmacie Mahamasina", "Mahamasina")
    )
)

//-----------------------------------------ACTIVITES------------------------------------------------
val activitesTest = listOf(
    Activite(1, "Canal Olympia Andohatapenaka", "Cinéma", "Détente", "Andohatapenaka", -18.898000, 47.506000),
    Activite(2, "Padel Club Ivandry", "Padel", "Sport", "Ivandry", -18.868000, 47.531000),
    Activite(3, "Spa Le Louvre", "Spa", "Bien-être", "Antaninarenina", -18.908000, 47.524000),
    Activite(4, "Bowling Tana Waterfront", "Bowling", "Détente", "Waterfront", -18.891000, 47.524000),
    Activite(5, "Tennis Club Ankorondrano", "Tennis", "Sport", "Ankorondrano", -18.880000, 47.522000),
    Activite(6, "Musée Andafiavaratra", "Visite guidée", "Culture", "Andafiavaratra", -18.917000, 47.531000),
    Activite(7, "Salle de sport Fitness Park", "Musculation", "Sport", "Ankorondrano", -18.878000, 47.523000),
    Activite(8, "Parc Tsarasaotra", "Observation d'oiseaux", "Nature", "Alarobia", -18.870000, 47.535000),
    Activite(9, "Escape Game Tana", "Escape game", "Détente", "Isoraka", -18.912000, 47.518000),
    Activite(10, "Piscine Hôtel Panorama", "Natation", "Sport", "Ankorondrano", -18.876000, 47.520000)
)

//----------------------------------------URGENCE---------------------------------------------------
val contactsUrgenceTest = listOf(
    ContactUrgence(1, "Commissariat Analakely", "Police", "Analakely", "+261 20 22 227 35"),
    ContactUrgence(2, "Commissariat Antaninarenina", "Police", "Antaninarenina", "+261 20 22 202 22"),
    ContactUrgence(3, "Gendarmerie Ankorondrano", "Gendarmerie", "Ankorondrano", "+261 20 22 621 91"),
    ContactUrgence(4, "Commissariat Isoraka", "Police", "Isoraka", "+261 20 22 296 47"),
    ContactUrgence(5, "Gendarmerie Behoririka", "Gendarmerie", "Behoririka", "+261 20 22 341 08"),
    ContactUrgence(6, "Commissariat Ambohijatovo", "Police", "Ambohijatovo", "+261 20 22 253 19")
)

// Statique pour l'instant — sera calculé par géolocalisation plus tard
val contactPlusProcheTest = contactsUrgenceTest[0]

//----------------------------------------DESTINATIONS----------------------------------------------
val destinationsTest = listOf(
    DestinationTouristique(1, "Nosy Be", R.drawable.dest_nosybe),
    DestinationTouristique(2, "Isalo", R.drawable.dest_isalo),
    DestinationTouristique(3, "Andasibe", R.drawable.dest_andasibe),
    DestinationTouristique(4, "Ranomafana", R.drawable.dest_ranomafana),
    DestinationTouristique(5, "Antsirabe", R.drawable.dest_antsirabe),
    DestinationTouristique(6, "Sainte-Marie", R.drawable.dest_saintemarie)
)

//----------------------------------------WEATHER---------------------------------------------------
// Valeurs réelles récupérées pour Antananarivo aujourd'hui (17 sept. 2026) — statique pour l'instant,
// à remplacer par un vrai appel API météo plus tard.
val weatherTodayTest = WeatherToday(
    temperatureActuelle = 11.9,
    conditionText = "Partiellement nuageux",
    emoji = "⛅",
    tempMax = 22.4,
    tempMin = 9.9,
    chancePluie = 10
)

val weatherWeekTest = listOf(
    WeatherDay("Aujourd'hui", "⛅", 22.4, 9.9, 10),
    WeatherDay("Vendredi 18 sept.", "☀️", 23.9, 11.8, 0),
    WeatherDay("Samedi 19 sept.", "☀️", 25.9, 13.8, 0),
    WeatherDay("Dimanche 20 sept.", "🌤️", 22.9, 12.6, 0),
    WeatherDay("Lundi 21 sept.", "🌥️", 24.9, 13.6, 15),
    WeatherDay("Mardi 22 sept.", "🌧️", 25.5, 14.2, 70),
    WeatherDay("Mercredi 23 sept.", "🌧️", 25.2, 14.0, 65),
    WeatherDay("Jeudi 24 sept.", "🌦️", 25.8, 13.9, 45)
)