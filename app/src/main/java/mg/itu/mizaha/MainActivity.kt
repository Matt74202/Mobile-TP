package mg.itu.mizaha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mg.itu.mizaha.data.restaurantsTest
import mg.itu.mizaha.data.*
import mg.itu.mizaha.ui.RestaurantsScreen
import mg.itu.mizaha.ui.UrgenceScreen
import mg.itu.mizaha.ui.nav.BottomNavBar
import mg.itu.mizaha.ui.nav.Destination
import mg.itu.mizaha.ui.AccueilScreen
import mg.itu.mizaha.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomNavBar(navController) }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Destination.Accueil.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Destination.Accueil.route) {
                                AccueilScreen(navController = navController)
                            }
                            composable(Destination.Urgence.route) {
                                UrgenceScreen()
                            }
                            composable(Destination.Autre.route) {
                                UrgenceScreen() // placeholder temporaire, à remplacer plus tard
                            }
                            composable("restaurants") {
                                RestaurantsScreen(restaurants = restaurantsTest)
                            }
                            composable("hotels") {
                                HotelsScreen(hotels = hotelsTest)
                            }
                            composable("banques") {
                                BanquesScreen(banques = banquesTest)
                            }
                            composable("pharmacies") {
                                PharmaciesScreen(
                                    pharmacies = pharmaciesTest,
                                    semaineDeGarde = semaineDeGardeTest
                                )
                            }
                            composable("activites") {
                                ActivitesScreen(activites = activitesTest)
                            }
                        }
                    }
                }
            }
        }
    }
}