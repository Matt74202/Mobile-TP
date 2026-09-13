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