package mg.itu.mizaha.data.entities

data class WeatherToday(
    val temperatureActuelle: Double,
    val conditionText: String,
    val emoji: String,
    val tempMax: Double,
    val tempMin: Double,
    val chancePluie: Int
)

data class WeatherDay(
    val jour: String,       // ex: "Vendredi 18 sept."
    val emoji: String,
    val tempMax: Double,
    val tempMin: Double,
    val chancePluie: Int
)