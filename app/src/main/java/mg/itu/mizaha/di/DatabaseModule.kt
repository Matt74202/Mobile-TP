package mg.itu.mizaha.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mg.itu.mizaha.data.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "mizaha.db")
            .fallbackToDestructiveMigration() // à retirer une fois en prod
            .build()

    @Provides fun provideRestaurantDao(db: AppDatabase) = db.restaurantDao()
    @Provides fun provideHotelDao(db: AppDatabase) = db.hotelDao()
    @Provides fun provideBanqueDao(db: AppDatabase) = db.banqueDao()
    @Provides fun providePharmacieDao(db: AppDatabase) = db.pharmacieDao()
    @Provides fun provideUserPreferenceDao(db: AppDatabase) = db.userPreferenceDao()
}