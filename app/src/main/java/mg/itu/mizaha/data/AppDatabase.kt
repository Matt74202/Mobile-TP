package mg.itu.mizaha.data

import androidx.room.Database
import androidx.room.RoomDatabase
import mg.itu.mizaha.data.dao.*
import mg.itu.mizaha.data.entities.*

@Database(
    entities = [Restaurant::class, Hotel::class, Banque::class, Pharmacie::class, UserPreference::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun hotelDao(): HotelDao
    abstract fun banqueDao(): BanqueDao
    abstract fun pharmacieDao(): PharmacieDao
    abstract fun userPreferenceDao(): UserPreferenceDao
}